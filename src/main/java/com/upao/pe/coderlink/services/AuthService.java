package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.developer.CreateDeveloperRequest;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.user.*;
import com.upao.pe.coderlink.exceptions.ExpiredTokenException;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.exceptions.UsedEmailException;
import com.upao.pe.coderlink.models.Customer;
import com.upao.pe.coderlink.models.Developer;
import com.upao.pe.coderlink.models.Token;
import com.upao.pe.coderlink.models.User;
import com.upao.pe.coderlink.repos.UserRepository;
import com.upao.pe.coderlink.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired private TokenService tokenService;
    @Autowired private CustomerService customerService;
    @Autowired private DeveloperService developerService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private JwtUserDetailsService userDetailsService;
    @Autowired private EmailService emailService;

    public CustomerDTO registerCustomer(CreateCustomerRequest request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        if(!request.getTypeUser().equalsIgnoreCase("CUSTOMER")){
            throw new ResourceNotExistsException("Wrong Role");
        }
        Customer customer = customerService.createCustomer(request);
        tokenService.sendEmail(customer);
        return customerService.returnCustomerDTO(customer);
    }

    public DeveloperDTO registerDeveloper(CreateDeveloperRequest request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        if(!request.getTypeUser().equalsIgnoreCase("DEVELOPER")){
            throw new ResourceNotExistsException("Wrong Role");
        }
        Developer developer = developerService.createDeveloper(request);
        tokenService.sendEmail(developer);
        return developerService.returnDeveloperDTO(developer);
    }

    public String activateAccount(String token){
        Token confirmationToken = tokenService.getToken(token);
        if(confirmationToken.getActivationDate() != null){
            throw new UsedEmailException("This email has been used");
        }
        LocalDateTime expirationDate = confirmationToken.getExpirationDate();
        if(expirationDate.isBefore(LocalDateTime.now())){
            throw new ExpiredTokenException("Expired token");
        }
        confirmationToken.setActivationDate(LocalDateTime.now());
        tokenService.saveChanges(confirmationToken);
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.saveAndFlush(user);
        return " <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"100%\">\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: center; padding: 50px 0;\">\n" +
                "        <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"600\"\n" +
                "          style=\"background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);\">\n" +
                "          <tr>\n" +
                "            <td style=\"padding: 40px;\">\n" +
                "              <h6 style=\"text-align: center; font-size: 18px; color: #000; text-transform: uppercase; font-weight: bold; font-family: 'Roboto Mono', monospace;\">\n" +
                "                CoderLink</h6>\n" +
                "              <h3 style=\"text-align: center; font-size: 24px; color: #007bff; font-weight: bold; font-family: 'Roboto', sans-serif;\">\n" +
                "                ACTIVACIÓN DE CUENTA</h3>\n" +
                "              <p style=\"text-align: center; font-size: 18px; color: #000;\">Se activó la cuenta correctamente.</p>\n" +
                "              <div style=\"text-align: center; margin-top: 30px;\">\n" +
                "                <a href=\"https://coderlink.netlify.app/login\"\n" + //Poner link del login del front en donde esta el "#"
                "                  style=\"display: inline-block; padding: 12px 24px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; font-size: 18px; font-weight: bold;\">\n" +
                "                  Iniciar sesión\n" +
                "                </a>\n" +
                "              </div>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>";
    }

    public AuthResponse login(AuthenticationUserRequest request){
        Authentication authentication = authenticate(request.getUsername(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);

        return new AuthResponse(request.getUsername(), "Authentication successfuly", accessToken, true);
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username or Password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public RecoveryPasswordResponse recoveryPassword(RecoveryPasswordRequest request) {
        if(!userRepository.existsUserByEmail(request.getEmail())){
            throw new ResourceNotExistsException("No user was found linked to this email");
        }
        User user = userRepository.findByEmail(request.getEmail());
        String token = tokenService.getTokenByUser(user).getToken();
        String url = "https://coderlink.netlify.app/change-password/"+token;
        String message = "Hola "+user.getUsername()+" vemos que olvidaste tu contraseña y en CoderLink nos gusta la tranquilidad de nuestros usuarios."+
                "Ingresa a este link para que reestablezcas tu contraseña y puedas seguir disfrutando las funcioens de CoderLink."+
                "Link: "+url;
        emailService.sendEmail(request.getEmail(), "Reestablecer Contraseña", message);
        return new RecoveryPasswordResponse("Email sended");
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest request){
        if(!request.getPassword().equalsIgnoreCase(request.getConfirmationPassword())){
            throw new RuntimeException("Different Password");
        }
        User user = tokenService.getToken(request.getToken()).getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.saveAndFlush(user);
        return new ChangePasswordResponse("Password changed");
    }
}

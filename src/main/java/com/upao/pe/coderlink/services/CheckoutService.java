package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.email.Mail;
import com.upao.pe.coderlink.dtos.payment.PaymentCaptureResponse;
import com.upao.pe.coderlink.dtos.payment.PaymentDTO;
import com.upao.pe.coderlink.dtos.payment.PaymentOrderResponse;
import com.upao.pe.coderlink.dtos.paypal.OrderCaptureResponse;
import com.upao.pe.coderlink.dtos.paypal.OrderResponse;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.models.enums.PostulationStatus;
import com.upao.pe.coderlink.repos.JobOfferRepository;
import com.upao.pe.coderlink.repos.PostulationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckoutService {

    @Autowired PayPalService payPalService;
    @Autowired PaymentService paymentService;
    @Autowired EmailService emailService;
    @Autowired CustomerService customerService;
    @Autowired
    PostulationRepository postulationRepository;
    @Autowired JobOfferService jobOfferService;

    @Value("${email.sender}")
    private String mailFrom;

    public PaymentOrderResponse createPayment(Long paymentId, String returnUrl, String cancelUrl){
        OrderResponse orderResponse =payPalService.createOrder(paymentId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);
    }

    public PaymentCaptureResponse capturePayment(String orderId) throws MessagingException {
        OrderCaptureResponse orderCaptureResponse = payPalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().getFirst().getReferenceId();
            PaymentDTO paymentDTO = paymentService.confirmPurchase(Long.parseLong(purchaseIdStr));
            paypalCaptureResponse.setPurchaseId(Math.toIntExact(paymentDTO.getIdPayment()));
            JobOffer jobOffer = jobOfferService.getJobOffer(paymentDTO.getJobOffer().getId());
            jobOffer.getPostulation().setStatus(PostulationStatus.ACEPTED);
            postulationRepository.saveAndFlush(jobOffer.getPostulation());
            sendPurchaseConfirmationEmail(paymentDTO);

        }
        return paypalCaptureResponse;
    }

    private void sendPurchaseConfirmationEmail(PaymentDTO paymentDTO) throws MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        String userEmail = customerService.getCustomer(user).getEmail();


        Map<String, Object> model = new HashMap<>();
        model.put("user", userEmail);
        model.put("total", paymentDTO.getTotal());
        model.put("orderUrl", "http://localhost:4200/order/" + paymentDTO.getIdPayment());


        Mail mail = emailService.createMail(
                userEmail,
                "Confirmación de Transferencia",
                model,
                mailFrom
        );
        emailService.sendEmail(mail,"email/payment-confirmation-template");
    }
}

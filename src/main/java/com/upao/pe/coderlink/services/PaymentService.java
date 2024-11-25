package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.offer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.payment.CreatePaymentRequest;
import com.upao.pe.coderlink.dtos.payment.PaymentDTO;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.exceptions.ResourceNotFoundException;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.models.Payment;
import com.upao.pe.coderlink.models.Postulation;
import com.upao.pe.coderlink.models.Project;
import com.upao.pe.coderlink.models.enums.PaymentStatus;
import com.upao.pe.coderlink.models.enums.PostulationStatus;
import com.upao.pe.coderlink.models.enums.ProjectStatus;
import com.upao.pe.coderlink.repos.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired private JobOfferService jobOfferService;
    @Autowired private PostulationService postulationService;
    @Autowired private ProjectService projectService;

    public PaymentDTO createPayment(CreatePaymentRequest request){
        JobOffer jobOffer = jobOfferService.getJobOffer(request.getJobOfferId());
        Payment payment = new Payment(null, LocalDateTime.now(), request.getTotal(), null, request.getPaymentMethod(), request.getFacturation(), null, PaymentStatus.PENDING, jobOffer);
        return returnPaymentDTO(paymentRepository.save(payment));
    }

    public PaymentDTO hireDev(Long id){
        Payment payment = getPayment(id);
        payment.setStatus(PaymentStatus.PAID);
        payment.setTotalDate(LocalDateTime.now());
        Postulation postulation = payment.getJobOffer().getPostulation();
        postulation.setStatus(PostulationStatus.ACEPTED);
        postulationService.saveChanges(postulation);
        Project project = postulation.getProject();
        project.setStatus(ProjectStatus.PROGRESS);
        projectService.saveChanges(project);
        paymentRepository.saveAndFlush(payment);
        return returnPaymentDTO(payment);
    }

    public PaymentDTO returnPaymentDTO(Payment payment){
        JobOfferDTO jobOfferDTO = jobOfferService.returnJobOfferDTO(payment.getJobOffer());
        return new PaymentDTO(payment.getIdPayment(), payment.getTransactionDate(), payment.getTotal(), payment.getTotalDate(), payment.getPaymentMethod(), payment.getFacturation(), payment.getStatus().toString(), jobOfferDTO);
    }

    public Payment getPayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isEmpty()){
            throw new ResourceNotExistsException("This payment does not exists");
        }
        return payment.get();
    }

    @Transactional
    public PaymentDTO confirmPurchase(Long paymentId) {
        // Obtener la entidad Purchase directamente desde el repositorio
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));

        // Confirmar la compra y calcular el total
       /* Float total = purchase.getItems()
                .stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0f, Float::sum);

        purchase.setTotal(total);*/
        payment.setStatus(PaymentStatus.PAID);

        // Guardar y retornar el DTO actualizado
        Payment updatedPurchase = paymentRepository.save(payment);
        return returnPaymentDTO(updatedPurchase);
    }

    public List<PaymentDTO> listPayments() {
        return paymentRepository.findAll().stream().map(this::returnPaymentDTO).toList();
    }
}

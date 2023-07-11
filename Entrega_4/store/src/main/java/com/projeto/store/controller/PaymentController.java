package com.projeto.store.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projeto.store.model.Jogo;
import com.projeto.store.model.Payment;
import com.projeto.store.repository.UserRepository;
import com.projeto.store.services.JogoService;
import com.projeto.store.services.PaymentService;
import com.projeto.store.services.UserService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    
    
    public PaymentController(UserRepository userRepository, PaymentService paymentService) {
    this.userRepository = userRepository;
    this.paymentService = paymentService;
    }

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private JogoService jogoService;

    @Autowired
    UserService userService;

@PostMapping("/process/{jogoId}")
@ResponseStatus(HttpStatus.CREATED)
public String createPayment(
         @PathVariable("jogoId") String gameId,
        @RequestParam("userName") String userName,
        @RequestParam("cvv") int cvv,
        @RequestParam("creditCard") String creditCard,
        @RequestParam("email") String email,
        
        //  @RequestParam("email") String email,
        Model model) {
    if (!verifyName(userName)) {
        model.addAttribute("error", "Invalid name");
        return "paymentProcess";
    }
    
    // Validar CVV
    if (!verifyCVV(cvv)) {
        model.addAttribute("error", "Invalid CVV");
        return "paymentProcess";
    }

    // Validar cartão
    String cleanedCreditCard = creditCard.replaceAll("\\D", "");
    if (!verifyCardNumber(cleanedCreditCard)) {
        model.addAttribute("error", "Invalid credit card");
        return "paymentProcess";
    }

    if(!verifyEmail(email)){
        model.addAttribute("error", "Invalid email");
        return "paymentProcess";
    }

    

    // Resto do código para criar o pagamento
    Payment payment = new Payment();
    payment.setUserName(userName);
    payment.setCvv(cvv);
    payment.setCreditCard(cleanedCreditCard);
    payment.setJogoId(gameId);
    payment.setUserEmail(email);
    
     payment.setPaymentDate(LocalDate.now().toString());
    double amount =  jogoService.getPriceById(gameId);
    payment.setAmount(amount);

    String nomeJogoString = jogoService.getNameById(gameId);
    payment.setJogoNome(nomeJogoString);

    String userId = userService.getUserIdByEmail(email);
    payment.setUserId(userId);

      if (userService.verifyExistingGameInAccount(userId, gameId)) {
         model.addAttribute("gameExists", true);
        return "paymentProcess";
    }
    

   userService.addJogo(userId, gameId);

    Payment createdPayment = paymentService.createPayment(payment);
   

    return "redirect:/payment/process?id=" + gameId;
}

@GetMapping("/process/{jogoId}")
public String showPaymentProcessPage(@PathVariable String jogoId, Model model) {
    Jogo jogo = jogoService.getJogoById(jogoId);
    
    if (jogo == null) {
        model.addAttribute("error", "Jogo não encontrado");
        return "error";
    }
    
    model.addAttribute("jogo", jogo); // Adicionar o jogo ao modelo
    
    return "paymentProcess";
}

    @GetMapping("/user/{userId}")
    @ResponseBody
    public Payment getPaymentByUserId(@PathVariable String userId) {
        return paymentService.getPaymentByUserId(userId);
    }

    @GetMapping("/{paymentId}")
    @ResponseBody
    public Payment getPaymentById(@PathVariable String paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @DeleteMapping("/{paymentId}")
    @ResponseBody
    public String deletePayment(@PathVariable String paymentId) {
        return paymentService.deletePayment(paymentId);
    }

    @PutMapping("/{paymentId}")
    @ResponseBody
    public Payment updatePayment(@PathVariable String paymentId, @RequestBody Payment paymentRequest) {
        return paymentService.updatePayment(paymentId, paymentRequest);
    }

    @PutMapping("/{paymentId}/approve")
    @ResponseBody
    public boolean approvePayment(@PathVariable String paymentId) {
        return paymentService.approvePayment(paymentId);
    }

    @GetMapping
    public String showPaymentPage(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment";
    }

    public boolean verifyCardNumber(String cardNumber) {
        cardNumber = cardNumber.replaceAll("\\D", "");

        // Verificar se a string possui apenas dígitos numéricos
        if (!cardNumber.matches("\\d+")) {
            return false;
        }

        int[] digits = new int[cardNumber.length()];
        int sum = 0;
        int digit = 0;

        for (int i = 0; i < cardNumber.length(); i++) {
            digits[i] = Integer.parseInt(cardNumber.substring(i, i + 1));
        }

        for (int i = 0; i < cardNumber.length(); i++) {
            if (i % 2 == cardNumber.length() % 2) {
                digit = digits[i] * 2;
                if (digit > 9) {
                    digit -= 9;
                }
            } else {
                digit = digits[i];
            }
            sum += digit;
        }

        return sum % 10 == 0;
    }

    public boolean verifyCVV(int cvv) {
        return cvv >= 100 && cvv <= 999;
    }

    public boolean verifyName(String name) {
        return name.matches("[a-zA-Z]+") && !name.isEmpty() && !name.matches(".*\\d.*");

    }

    public boolean verifyEmail(String email) {
        if(email.isEmpty()){
            return false;
        }
        if(!email.contains("@")){
            return false;
        }

        if(userService.getUserByEmail(email) == null){
            return false;
        }

        return true;
    }


}

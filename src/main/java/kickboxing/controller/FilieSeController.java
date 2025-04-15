package kickboxing.controller;

import kickboxing.model.FilieSe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.mail.internet.MimeMessage;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FilieSeController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/enviarFormularioFilieSe")
    public String enviarFormulario(@ModelAttribute FilieSe form,
                                   @RequestParam("documentosFilie") MultipartFile[] arquivos,
                                   RedirectAttributes redirectAttributes) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("federacao.kickboxing.rs@gmail.com");
            helper.setFrom(form.getEmailFilie(), form.getNomeFilie());
            helper.setSubject("Novo Pedido de Filiação");

            String corpoEmail = "<strong>Nome:</strong> " + form.getNomeFilie() + "<br>" +
                    "<strong>Email:</strong> " + form.getEmailFilie() + "<br>" +
                    "<strong>Endereço:</strong> " + form.getEnderecoFilie() + "<br>" +
                    "<strong>Cidade/Estado:</strong> " + form.getCidadeFilie() + "<br>" +
                    "<strong>Graduação:</strong> " + form.getGraduacaoFilie() + "<br>" +
                    "<strong>WhatsApp:</strong> " + form.getContatoFilie() + "<br><br>" +
                    "<strong>Trajetória:</strong><br>" + form.getDescricaoFilie();

            helper.setText(corpoEmail, true);

            for (MultipartFile file : arquivos) {
                if (!file.isEmpty()) {
                    helper.addAttachment(file.getOriginalFilename(), file);
                }
            }

            javaMailSender.send(message);
            redirectAttributes.addFlashAttribute("successMessage", "Formulário enviado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao enviar formulário: " + e.getMessage());
        }

        return "redirect:/filiePub";
    }

}

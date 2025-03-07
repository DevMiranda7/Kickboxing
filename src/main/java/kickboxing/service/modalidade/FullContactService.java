package kickboxing.service.modalidade;

import kickboxing.model.modalide.FullContact;
import kickboxing.repository.modalidade.FullContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FullContactService {

    @Autowired
    private FullContactRepository fullContactRepository;

    @Transactional
    public void cadastrarFullContact(String nomeFullContact, String pontosFullContact) {
        try {
            String pontosFormatado = pontosFullContact.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            FullContact fullContact = new FullContact();
            fullContact.setNomeFullContact(nomeFullContact.trim());
            fullContact.setPontosFullContact(pontos);

            fullContactRepository.save(fullContact);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }
}

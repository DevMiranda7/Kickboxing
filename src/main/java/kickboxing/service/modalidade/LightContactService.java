package kickboxing.service.modalidade;

import kickboxing.model.modalide.LightContact;
import kickboxing.repository.modalidade.LightContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LightContactService {

    @Autowired
    private LightContactRepository lightContactRepository;

    public List<LightContact> listarLightContact() {
        List<LightContact> lightContacts = lightContactRepository.findAll();
        return lightContacts.stream()
                .sorted((lc1, lc2) -> lc2.getPontosLightContact().compareTo(lc1.getPontosLightContact()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cadastrarLightContact(String nomeLightContact, String pontosLightContact) {
        try {
            String pontosFormatado = pontosLightContact.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            LightContact lightContact = new LightContact();
            lightContact.setNomeLightContact(nomeLightContact.trim());
            lightContact.setPontosLightContact(pontos);

            lightContactRepository.save(lightContact);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }

    public void excluirLightContact(Long id) {
        LightContact lightContact = lightContactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado"));

        lightContactRepository.deleteById(id);
    }
}
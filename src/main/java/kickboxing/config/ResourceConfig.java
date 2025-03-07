package kickboxing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    private static final String UPLOAD_PATRONCINADORES_DIR = Paths.get(System.getProperty("user.dir"),
            "src/main/resources/static/upload/patrocinadores/").toString();

    private static final String UPLOAD_ACADEMIAS_DIR = Paths.get(System.getProperty("user.dir"),
            "src/main/resources/static/upload/academias/").toString();

    private static final String UPLOAD_EVENTOS_DIR = Paths.get(System.getProperty("user.dir"),
            "src/main/resources/static/upload/eventos/").toString();

    private static final String UPLOAD_FILIADOS_DIR = Paths.get(System.getProperty("user.dir"),
            "src/main/resources/static/upload/filiados/").toString();

    private static final String UPLOAD_PROFESSORES_DIR = Paths.get(System.getProperty("user.dir"),
            "src/main/resources/static/upload/professores/").toString();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/upload/patrocinadores/**")
                .addResourceLocations("file:" + UPLOAD_PATRONCINADORES_DIR + "/");

        registry.addResourceHandler("/upload/academias/**")
                .addResourceLocations("file:" + UPLOAD_ACADEMIAS_DIR + "/");

        registry.addResourceHandler("/upload/eventos/**")
                .addResourceLocations("file:" + UPLOAD_EVENTOS_DIR + "/");

        registry.addResourceHandler("/upload/filiados/**")
                .addResourceLocations("file:" + UPLOAD_FILIADOS_DIR + "/");

        registry.addResourceHandler("/upload/professores/**")
                .addResourceLocations("file:" + UPLOAD_PROFESSORES_DIR + "/");
    }
}
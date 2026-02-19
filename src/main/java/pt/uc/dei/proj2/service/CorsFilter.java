package pt.uc.dei.proj2.service;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        // Permite pedidos vindos da origem do seu Live Server
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:63342");

        // Autoriza as credenciais (necessário se usar cookies ou autenticação específica)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // Autoriza os cabeçalhos personalizados que está a enviar no fetch (username, password, etc)
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, username, password");

        // Autoriza os métodos HTTP que a sua API utiliza
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
package pt.uc.dei.proj2.service;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    // Trata o preflight (OPTIONS)
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {

            Response response = Response.ok()
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization, username, password")
                    .build();

            requestContext.abortWith(response);
        }
    }

    // Adiciona os headers CORS às respostas normais
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization, username, password");
    }
}
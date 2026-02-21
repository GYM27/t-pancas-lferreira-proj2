package pt.uc.dei.proj2.service;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

import pt.uc.dei.proj2.service.ClientesService;
import pt.uc.dei.proj2.service.UserService;
import pt.uc.dei.proj2.service.LeadsService;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ClientesService.class);
        resources.add(UserService.class);
        resources.add(LeadsService.class);
        resources.add(CorsFilter.class);
        return resources;
    }
}
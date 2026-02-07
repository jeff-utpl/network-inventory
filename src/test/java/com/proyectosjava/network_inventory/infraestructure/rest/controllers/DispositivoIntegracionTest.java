package com.proyectosjava.network_inventory.infraestructure.rest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //Levanta todo el contexto de Spring (el edificio completo)
public class DispositivoIntegracionTest {

    @Autowired
    private MockMvc mockMvc; //Es nuestro 'Postman' interno para tests

    @Test
    void cuandoPeticionNoTieneToken_entoncesRetorna403() throws Exception {
        mockMvc.perform(post("/api/dispositivos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Router1\", \"ip\":\"192.168.1.1\", \"tipo\":\"ROUTER\"}"))
                .andExpect(status().isForbidden()); //Esperamos que lo bloquee el 'guardia'
    }

    @Test
    void cuandoPeticionTieneTokenValido_entoncesCreaDispositivo() throws Exception {
        //1. GIVEN: Obtenemos un token primero (Simulando Login)
        String loginJson ="{\"username\":\"admin\", \"password\":\"1234\"}";

        String respuestaLogin = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Extraemos el token del JSON
        String token = respuestaLogin.split(":")[1].replace("\"", "").replace("}", "");

        //2. WHEN: Enviamos la petición con el token en el Header
        String dispositivoJson = "{\"nombre\":\"Switch-Core\", \"ip\":\"10.0.0.1\", \"tipo\":\"SWITCH\"}";

        mockMvc.perform(post("/api/dispositivos")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dispositivoJson))

                //3. THEN: El 'guardia' nos deja pasar y el servicio responde 200/201
                .andExpect(status().isCreated());
    }
}

package com.laila.pet_symptom_tracker.entities.breed;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.breed.dto.GetBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetTypeCompact;
import com.laila.pet_symptom_tracker.entities.user.UserService;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import com.laila.pet_symptom_tracker.securityconfig.JwtAuthFilter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = BreedController.class)
@AutoConfigureMockMvc(addFilters = false)
class BreedControllerTest {
  @Autowired MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private BreedService breedService;
  @MockitoBean private UserService userService;
  @MockitoBean private JwtAuthFilter jwtAuthFilter;
  @MockitoBean private Authentication authentication;
  @MockitoBean private AuthenticationService authenticationService;

  @Test
  @WithMockUser(username = "user")
  void createBreed() throws Exception {
    PostBreed postBreed = new PostBreed("Siamese", 1L);
    GetBreed createdBreed = new GetBreed(1L, "Siamese", new GetPetTypeCompact(1L, "Cat"), "user");

    when(breedService.create(any(PostBreed.class))).thenReturn(createdBreed);

    mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postBreed)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Siamese"));
  }

  @Test
  void getAllBreeds() throws Exception {
    List<GetBreed> breeds =
        List.of(
            new GetBreed(1L, "Siamese", new GetPetTypeCompact(1L, "Cat"), "Moderator"),
            new GetBreed(2L, "Rotweiler", new GetPetTypeCompact(2L, "Dog"), "Moderator"),
            new GetBreed(3L, "Flemish Giant", new GetPetTypeCompact(3L, "Rabbit"), "Moderator"),
            new GetBreed(4L, "Parrot", new GetPetTypeCompact(4L, "Bird"), "ModOne"));

    when(breedService.getAll()).thenReturn(breeds);
    ResultActions result =
        mvc.perform(get(Routes.BREEDS))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(breeds.size()));

    for (int i = 0; i < breeds.size(); i++) {
      result
          .andExpect(jsonPath("$[" + i + "].id").value(breeds.get(i).id()))
          .andExpect(jsonPath("$[" + i + "].name").value(breeds.get(i).name()))
          .andExpect(jsonPath("$[" + i + "].petType.id").value(breeds.get(i).petType().id()))
          .andExpect(jsonPath("$[" + i + "].petType.name").value(breeds.get(i).petType().name()))
          .andExpect(jsonPath("$[" + i + "].createdBy").value(breeds.get(i).createdBy()));
    }
  }
}

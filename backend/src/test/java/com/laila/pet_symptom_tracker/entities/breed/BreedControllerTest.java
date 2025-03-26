package com.laila.pet_symptom_tracker.entities.breed;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.breed.dto.GetBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PatchBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetTypeCompact;
import com.laila.pet_symptom_tracker.entities.user.UserService;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import com.laila.pet_symptom_tracker.securityconfig.JwtAuthFilter;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = BreedController.class)
@AutoConfigureMockMvc(addFilters = false)
class BreedControllerTest {
  @Autowired MockMvc mvc;
  PostBreed postBreed;
  GetBreed getBreed;
  GetPetTypeCompact petType;
  List<GetBreed> breeds;
  Long breedId;
  @Autowired private ObjectMapper objectMapper;
  @MockitoBean private BreedService breedService;
  @MockitoBean private UserService userService;
  @MockitoBean private JwtAuthFilter jwtAuthFilter;
  @MockitoBean private Authentication authentication;
  @MockitoBean private AuthenticationService authenticationService;

  @BeforeEach
  public void init() {
    breedId = 1L;
    postBreed = new PostBreed("Siamese", 1L);
    getBreed = new GetBreed(1L, "Siamese", new GetPetTypeCompact(1L, "Cat"), "Moderator");
    petType = new GetPetTypeCompact(1L, "Cat");
    breeds =
        List.of(
            new GetBreed(1L, "Siamese", new GetPetTypeCompact(1L, "Cat"), "Moderator"),
            new GetBreed(2L, "Rotweiler", new GetPetTypeCompact(2L, "Dog"), "Moderator"),
            new GetBreed(3L, "Flemish Giant", new GetPetTypeCompact(3L, "Rabbit"), "Moderator"),
            new GetBreed(4L, "Parrot", new GetPetTypeCompact(4L, "Bird"), "ModOne"));
  }

  @Test
  @WithMockUser(username = "user")
  void createBreed() throws Exception {
    given(breedService.create(ArgumentMatchers.any())).willReturn(getBreed);

    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postBreed)));

    response
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(getBreed.id().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(getBreed.name())))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.petType.name", CoreMatchers.is(getBreed.petType().name())))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.petType.id", CoreMatchers.is(getBreed.id().intValue())))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.createdBy", CoreMatchers.is(getBreed.createdBy())));
  }

  @Test
  void getAllBreeds() throws Exception {
    when(breedService.getAll()).thenReturn(breeds);

    ResultActions response =
        mvc.perform(get(Routes.BREEDS).contentType(MediaType.APPLICATION_JSON));

    response
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(breeds.size())));
  }

  @Test
  void GetBreedById() throws Exception {
    when(breedService.getById(1L)).thenReturn(getBreed);

    mvc.perform(get(Routes.BREEDS + "/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Siamese"))
        .andExpect(jsonPath("$.petType.id").value(1L))
        .andExpect(jsonPath("$.petType.name").value("Cat"))
        .andExpect(jsonPath("$.createdBy").value("Moderator"));
  }

  @Test
  void GetNonExistentBreedById() throws Exception {
    when(breedService.getById(breedId)).thenThrow(new NotFoundException());

    mvc.perform(get(Routes.BREEDS + "/" + breedId.intValue())).andExpect(status().isNotFound());
  }

  @Test
  public void updateBreed() throws Exception {
    PatchBreed patch = new PatchBreed("Changed", null);
    GetBreed patchedBreed = new GetBreed(breedId, "Changed", petType, "Moderator");

    when(breedService.patch(breedId, patch)).thenReturn(patchedBreed);

    ResultActions response =
        mvc.perform(
            patch(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patch)));

    response
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.id").value(patchedBreed.id().intValue()))
        .andExpect(jsonPath("$.name").value(patch.name()))
        .andExpect(jsonPath("$.petType.id").value(patchedBreed.petType().id().intValue()))
        .andExpect(jsonPath("$.petType.name").value(patchedBreed.petType().name()))
        .andExpect(jsonPath("$.createdBy").value(patchedBreed.createdBy()));
  }

  @Test
  public void deleteBreed() throws Exception {
    doNothing().when(breedService).deleteById(breedId);

    ResultActions response =
        mvc.perform(
            delete(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void deleteNonExistingBreed() throws Exception {
    doThrow(new NotFoundException()).when(breedService).deleteById(breedId);

    ResultActions response =
        mvc.perform(
            delete(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  // TODO add unhappy paths!
}

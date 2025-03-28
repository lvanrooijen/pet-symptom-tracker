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
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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
  @DisplayName("Creating a new breed should return status code 201")
  void successful_post_breed_should_return_201() throws Exception {
    given(breedService.create(postBreed)).willReturn(getBreed);

    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postBreed)));

    response.andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @DisplayName("Creating a new breed should return the right response body")
  void create_breed_should_return_get_breed_dto() throws Exception {
    given(breedService.create(ArgumentMatchers.any())).willReturn(getBreed);

    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postBreed)));

    response
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
  @DisplayName("Creating a breed without a request body should return status code 400 ")
  void create_breed_without_request_body_should_return_status_code_400() throws Exception {
    given(breedService.create(postBreed)).willReturn(getBreed);

    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Create breed with empty name should return 404")
  void create_breed_with_blank_name_should_return_status_code_400() throws Exception {
    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PostBreed("", 1L))));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Create breed with name that has less then 3 characters should return 404")
  void create_breed_with_too_short_name_should_return_status_code_400() throws Exception {
    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PostBreed("ca", 1L))));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Create breed with name that has more then 30 characters should return 404")
  void create_breed_with_too_long_name_should_return_status_code_400() throws Exception {
    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new PostBreed("catcatcatcatcatcatcatcatcatcatcat", 1L))));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Create breed with missing name should return 404")
  void create_breed_with_missing_name_should_return_status_code_400() throws Exception {
    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PostBreed(null, 1L))));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Create breed with missing pet type id should return 404")
  void create_breed_with_missing_pet_type_id_should_return_status_code_400() throws Exception {
    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PostBreed("Cat", null))));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Create breed where the pet type id is not a number should return 404")
  void create_breed_with_missing_pet_type_id_NaN_should_return_status_code_400() throws Exception {
    String invalidJson =
        """
        {
            "name": "Cat",
            "petTypeId": "He"
        }
        """;

    ResultActions response =
        mvc.perform(
            post(Routes.BREEDS).contentType(MediaType.APPLICATION_JSON).content(invalidJson));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Get all breeds should return all available breeds")
  void get_all_should_return_all() throws Exception {
    when(breedService.getAll()).thenReturn(breeds);

    ResultActions response =
        mvc.perform(get(Routes.BREEDS).contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(breeds.size())));
  }

  @Test
  @DisplayName("Getting all breeds should return status code 200")
  void get_all_should_return_status_200() throws Exception {
    when(breedService.getAll()).thenReturn(breeds);

    ResultActions response =
        mvc.perform(get(Routes.BREEDS).contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("If get all breeds is an empty list it should return status code 204")
  void get_all_should_return_status_204_when_no_content() throws Exception {
    when(breedService.getAll()).thenReturn(new ArrayList<>());

    ResultActions response =
        mvc.perform(get(Routes.BREEDS).contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("Get breed by id should return the right dto")
  void get_by_id_should_return_getBreed() throws Exception {
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
  @DisplayName("Fetching a non existing breed should return status code 404")
  void get_non_existent_breed_by_id_should_return_status_code_404() throws Exception {
    when(breedService.getById(breedId)).thenThrow(new NotFoundException());

    mvc.perform(get(Routes.BREEDS + "/" + breedId.intValue())).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Patching a breed should return a patched breed")
  public void patch_breed_should_return_patched_breed() throws Exception {
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
  @DisplayName("Patching a breed should return status code 200")
  public void patch_breed_should_return_status_code_200() throws Exception {
    PatchBreed patch = new PatchBreed("Changed", null);
    GetBreed patchedBreed = new GetBreed(breedId, "Changed", petType, "Moderator");

    when(breedService.patch(breedId, patch)).thenReturn(patchedBreed);

    ResultActions response =
        mvc.perform(
            patch(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patch)));

    response.andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("Patching a breed without a request body should return status code 400")
  public void patch_breed_empty_request_body_should_return_status_code_400() throws Exception {
    ResultActions response =
        mvc.perform(
            patch(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Deleting a breed should return status code 200")
  public void delete_breed_should_return_status_code_200() throws Exception {
    doNothing().when(breedService).deleteById(breedId);

    ResultActions response =
        mvc.perform(
            delete(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("Deleting a non-existing breed should return status code 404")
  public void delete_non_existing_breed_should_return_status_code_404() throws Exception {
    doThrow(new NotFoundException()).when(breedService).deleteById(breedId);

    ResultActions response =
        mvc.perform(
            delete(Routes.BREEDS + "/" + breedId.intValue())
                .contentType(MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}

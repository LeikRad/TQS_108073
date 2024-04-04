package leikrad.dev.homework1.controllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.DisplayName;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.MediaType;

// Static imports
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Optional;


import leikrad.dev.homework1.boundary.CityController;
import leikrad.dev.homework1.service.CityManagerService;
import leikrad.dev.homework1.data.city.*;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityManagerService cityManagerService;
    
    @Test
    @DisplayName("Create City")
    void testCreateCity() throws Exception{
        City city = new City("Lisbon");

        when(cityManagerService.createCity(Mockito.any())).thenReturn(city);

        mvc.perform(post("/api/city")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.cityName", is("Lisbon")));

        verify(cityManagerService, times(1)).createCity(Mockito.any());
    }
    
    @Test
    @DisplayName("Create City with invalid id")
    void testCreateCityWithInvalidId() throws Exception{
        City city = new City("Lisbon");
        city.setCityId(1L);
        
        when(cityManagerService.createCity(Mockito.any())).thenThrow(new IllegalArgumentException("City id must be null"));

        mvc.perform(post("/api/city")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city)))
            .andExpect(status().isBadRequest());
        
        verify(cityManagerService, times(1)).createCity(Mockito.any());
    }

    @Test
    @DisplayName("Get all cities")
    void testGetAllCities() throws Exception {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");

        List<City> allCities = List.of(city1, city2, city3);

        when(cityManagerService.getAllCities()).thenReturn(allCities);

        mvc.perform(get("/api/city")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].cityName", is(city1.getCityName())))
            .andExpect(jsonPath("$[1].cityName", is(city2.getCityName())))
            .andExpect(jsonPath("$[2].cityName", is(city3.getCityName())));

        verify(cityManagerService, times(1)).getAllCities();
    }

    @Test
    @DisplayName("Get city by id")
    void testGetCityById() throws Exception {
        City city = new City("Lisbon");
        city.setCityId(1L);

        when(cityManagerService.getCityDetails(city.getCityId())).thenReturn(Optional.of(city));

        mvc.perform(get("/api/city/{id}", city.getCityId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cityName", is(city.getCityName())));

        verify(cityManagerService, times(1)).getCityDetails(city.getCityId());
    }

    @Test
    @DisplayName("Get city by id with invalid id")
    void testGetCityByIdWithInvalidId() throws Exception {
        when(cityManagerService.getCityDetails(-1L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/city/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(cityManagerService, times(1)).getCityDetails(-1L);
    }

    @Test
    @DisplayName("Delete city")
    void testDeleteCity() throws Exception {
        City city = new City("Lisbon");
        city.setCityId(1L);

        
        doNothing().when(cityManagerService).deleteCity(city.getCityId());

        mvc.perform(delete("/api/city/{id}", city.getCityId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        
        verify(cityManagerService, times(1)).deleteCity(city.getCityId());
    }

    @Test
    @DisplayName("Delete city with invalid id")
    void testDeleteCityWithInvalidId() throws Exception {
        
        doThrow(new EntityNotFoundException("City not found")).when(cityManagerService).deleteCity(-1L);

        mvc.perform(delete("/api/city/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        verify(cityManagerService, times(1)).deleteCity(-1L);
    }

    @Test
    @DisplayName("Update city")
    void testUpdateCity() throws Exception {
        City city = new City("Lisbon");
        city.setCityId(1L);

        when(cityManagerService.updateCity(Mockito.any())).thenReturn(city);

        mvc.perform(put("/api/city/{id}", city.getCityId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cityName", is(city.getCityName())));

        verify(cityManagerService, times(1)).updateCity(Mockito.any());
    }

    @Test
    @DisplayName("Update city with invalid id")
    void testUpdateCityWithInvalidId() throws Exception {
        City city = new City("Lisbon");
        city.setCityId(-1L);

        when(cityManagerService.updateCity(Mockito.any())).thenThrow(new EntityNotFoundException("City not found"));

        mvc.perform(put("/api/city/{id}", city.getCityId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city)))
            .andExpect(status().isNotFound());

        verify(cityManagerService, times(1)).updateCity(Mockito.any());
    }


}

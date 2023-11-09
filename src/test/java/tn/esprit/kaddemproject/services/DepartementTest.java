package tn.esprit.kaddemproject.services;


import tn.esprit.kaddemproject.entities.Departement;
import tn.esprit.kaddemproject.entities.Universite;
import tn.esprit.kaddemproject.repositories.DepartementRepository;
import tn.esprit.kaddemproject.repositories.UniversiteRepository;
import tn.esprit.kaddemproject.services.IUniversiteServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
public class DepartementTest {
    @Mock
    private DepartementRepository departementRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddDepartment() {
        Departement mockDepartment = new Departement();
        when(departementRepository.save(any(Departement.class))).thenReturn(mockDepartment);
        Departement departement = departementRepository.save(new Departement());
        assertNotNull(departement);
        verify(departementRepository, times(1)).save(any(Departement.class));
    }
    @Test
    public void testRetrieveAllDepartement() {
        List<Departement> mockDepartement = Arrays.asList(new Departement(), new Departement());
        when(departementRepository.findAll()).thenReturn(mockDepartement);

        List<Departement> departements = departementRepository.findAll();

        assertEquals(mockDepartement.size(), departements.size());
        verify(departementRepository, times(1)).findAll();
    }
    @Test
    public void testRetrieveDepartement() {
        Departement mockDepartment = new Departement();

        when(departementRepository.findById(anyInt())).thenReturn(Optional.of(mockDepartment));

        Departement  departement = departementRepository.findById(1).orElse(null);


        assertNotNull(departement);
        verify(departementRepository, times(1)).findById(anyInt());
    }
    @Test
    public void testDeleteDepartment() {
        Departement mockDepartment = new Departement();
        doNothing().when(departementRepository).deleteById(anyInt());
       departementRepository.deleteById(1);
        verify(departementRepository, times(1)).deleteById(eq(1));
    }

}

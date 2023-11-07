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

public class UniversiteTest {
    @InjectMocks
    private IUniversiteServiceImpl iUniversiteService;
    @Mock
    private UniversiteRepository universiteRepository ;
    @Mock
    private DepartementRepository departementRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddUniversity() {
        Universite mockuniversity = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(mockuniversity);
        Universite universite = universiteRepository.save(new Universite());
        assertNotNull(universite);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }
    @Test
    public void testRetrieveAllUniversities() {
        List<Universite> mockuniversities = Arrays.asList(new Universite(), new Universite());
        when(universiteRepository.findAll()).thenReturn(mockuniversities);

        List<Universite> universites = universiteRepository.findAll();

        assertEquals(mockuniversities.size(), universites.size());
        verify(universiteRepository, times(1)).findAll();
    }
    @Test
    public void testRetrieveUniversity() {
        Universite mockuniversity = new Universite();

        when(universiteRepository.findById(anyInt())).thenReturn(Optional.of(mockuniversity));

        Universite  universite = universiteRepository.findById(1).orElse(null);


        assertNotNull(universite);
        verify(universiteRepository, times(1)).findById(anyInt());
    }

    @Test
    public void retrieveDepartementsByUniversite(){
        Universite universiteMock = mock(Universite.class);

        // Create a sample Departement
        Departement departement1 = new Departement();
        Departement departement2 = new Departement();

        List<Departement> departements = Arrays.asList(departement1, departement2);

        // Mock the behavior of the universiteRepository when findById is called
        when(universiteRepository.findById(anyInt())).thenReturn(Optional.of(universiteMock));
        when(universiteMock.getDepartements()).thenReturn(departements);
        // Call the method to retrieve departements by Universite
        List<Departement> retrievedDepartements = iUniversiteService.retrieveDepartementsByUniversite(1);

        // Verify that the retrieved departements match the expected ones
        assertEquals(2, retrievedDepartements.size());
        assertTrue(retrievedDepartements.contains(departement1));
        assertTrue(retrievedDepartements.contains(departement2));
    }
    @Test
    public void testAssignUniversiteToDepartement() {
        // Create mock Universite and Departement
        Universite mockUniversite = new Universite();
        List<Departement> list = new ArrayList<>();
        mockUniversite.setDepartements(list);
        Departement mockDepartement = new Departement();

        // Define behavior of repository mocks
        when(universiteRepository.findById(anyInt())).thenReturn(Optional.of(mockUniversite));
        when(departementRepository.findById(anyInt())).thenReturn(Optional.of(mockDepartement));

        // Call the method to test
        iUniversiteService.assignUniversiteToDepartement(1, 2);

        // Verify that the mockDepartement has been added to the departements of mockUniversite
        assertTrue(mockUniversite.getDepartements().contains(mockDepartement));

        // Verify that the findById method was called once on both universiteRepository and departementRepository
        verify(universiteRepository, times(1)).findById(anyInt());
        verify(departementRepository, times(1)).findById(anyInt());

        // If your Universite class has a save method to persist changes, you should also verify that it was called
        // verify(universiteRepository, times(1)).save(mockUniversite);
    }


}

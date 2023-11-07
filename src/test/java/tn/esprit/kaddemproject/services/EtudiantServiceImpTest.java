package tn.esprit.kaddemproject.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.kaddemproject.entities.Departement;
import tn.esprit.kaddemproject.entities.Etudiant;
import tn.esprit.kaddemproject.entities.Equipe;
import tn.esprit.kaddemproject.repositories.DepartementRepository;
import tn.esprit.kaddemproject.repositories.EtudiantRepository;
import tn.esprit.kaddemproject.repositories.EquipeRepository;

import java.util.*;

public class EtudiantServiceImpTest {

    @InjectMocks
    private IEtudiantServiceImp etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;
    @Mock
    private DepartementRepository departementRepository;
    @Mock
    private EquipeRepository equipeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEtudiant() {
        Etudiant mockEtudiant = new Etudiant();
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(mockEtudiant);

        Etudiant etudiant = etudiantRepository.save(new Etudiant());

        assertNotNull(etudiant);
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    public void testRetrieveAllEtudiant() {
        List<Etudiant> mockEtudiant = Arrays.asList(new Etudiant(), new Etudiant());
        when(etudiantRepository.findAll()).thenReturn(mockEtudiant);

        List<Etudiant> factures = etudiantRepository.findAll();

        assertEquals(mockEtudiant.size(), factures.size());
        verify(etudiantRepository, times(1)).findAll();
    }


    @Test
    public void testRetrieveEtudiant() {
        Etudiant mockEtudiant = new Etudiant();
        when(etudiantRepository.findById(anyInt())).thenReturn(Optional.of(mockEtudiant));

        Etudiant etudiant = etudiantRepository.findById(1).orElse(null);

        assertNotNull(etudiant);
        verify(etudiantRepository, times(1)).findById(anyInt());
    }


    @Test
    public void testGetEtudiantByDepartement() {
        // Arrange
        List<Etudiant> expectedEtudiants = new ArrayList<>(Arrays.asList(new Etudiant(), new Etudiant()));
        when(etudiantRepository.findByDepartementIdDepart(1)).thenReturn(expectedEtudiants);

        // Act
        List<Etudiant> actualEtudiants = etudiantService.getEtudiantsByDepartement(1);

        // Assert
        assertEquals(expectedEtudiants.size(), actualEtudiants.size());
        verify(etudiantRepository, times(1)).findByDepartementIdDepart(1);
    }

    @Test
    public void testAssignEtudiantToDepartement() {
        // Arrange
        Departement mockDepartement =mock(Departement.class);
        Etudiant mockEtudiant = mock(Etudiant.class);

        when(departementRepository.findById(1)).thenReturn(Optional.of(mockDepartement));
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(mockEtudiant));

        // Act
        etudiantService.assignEtudiantToDepartement(1, 1);

        // Assert
        verify(departementRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).findById(1);
        verify(mockEtudiant, times(1)).setDepartement(mockDepartement);
    }





}


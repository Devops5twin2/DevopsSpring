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

public class IEquipeServiceImplTest {

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
    public void testAddEquipe() {
        Equipe mockEquipe = new Equipe();
        when(equipeRepository.save(any(Equipe.class))).thenReturn(mockEquipe);

        Equipe equipe = equipeRepository.save(new Equipe());

        assertNotNull(equipe);
        verify(equipeRepository, times(1)).save(any(Equipe.class));
    }

    @Test
    public void testRetrieveAllEquipe() {
        List<Equipe> mockEquipes = Arrays.asList(new Equipe(), new Equipe());
        when(equipeRepository.findAll()).thenReturn(mockEquipes);

        List<Equipe> equipes = equipeRepository.findAll();

        assertEquals(mockEquipes.size(), equipes.size());
        verify(equipeRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveEquipe() {
        Equipe mockEquipe = new Equipe();
        when(equipeRepository.findById(anyInt())).thenReturn(Optional.of(mockEquipe));

        Equipe equipe = equipeRepository.findById(1).orElse(null);

        assertNotNull(equipe);
        verify(equipeRepository, times(1)).findById(anyInt());
    }


}

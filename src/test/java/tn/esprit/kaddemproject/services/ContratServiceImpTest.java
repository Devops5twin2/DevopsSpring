package tn.esprit.kaddemproject.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.kaddemproject.entities.Contrat;
import tn.esprit.kaddemproject.entities.Etudiant;
import tn.esprit.kaddemproject.repositories.ContratRepository;
import tn.esprit.kaddemproject.repositories.EtudiantRepository;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class ContratServiceImpTest {
    @InjectMocks
    private IContratServiceImp contratService;

    @Mock
    private ContratRepository contratRepository;
    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddContrat() {
        Contrat mockContrat = new Contrat();
        when(contratRepository.save(any(Contrat.class))).thenReturn(mockContrat);

        Contrat contrat = contratRepository.save(new Contrat());

        assertNotNull(contrat);
        verify(contratRepository, times(1)).save(any(Contrat.class));
    }

    @Test
    public void testRetrieveAllContrats() {
        List<Contrat> mockContrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(mockContrats);

        List<Contrat> contrats = contratRepository.findAll();

        assertEquals(mockContrats.size(), contrats.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveContrat() {
        Contrat mockContrat = new Contrat();
        when(contratRepository.findById(anyInt())).thenReturn(Optional.of(mockContrat));

        Contrat contrat = contratRepository.findById(1).orElse(null);

        assertNotNull(contrat);
        verify(contratRepository, times(1)).findById(anyInt());
    }


    @Test
    public void testNbContratsValides() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        when(contratRepository.countByArchiveIsFalseAndDateDebutContratAfterAndDateFinContratBefore(startDate, endDate)).thenReturn(5);

        // Act
        Integer result = contratService.nbContratsValides(startDate, endDate);

        // Assert
        verify(contratRepository, times(1)).countByArchiveIsFalseAndDateDebutContratAfterAndDateFinContratBefore(startDate, endDate);
        assertEquals(5, result);
    }

    @Test
    public void testRetrieveAndUpdateStatusContrat() {
        // Arrange
        LocalDate currentDate = LocalDate.now();
        List<Contrat> mockContrats = new ArrayList<>();
        when(contratRepository.findByArchiveIsFalse()).thenReturn(mockContrats);

        // Act
        contratService.retrieveAndUpdateStatusContrat();

        // Assert
        verify(contratRepository, times(1)).findByArchiveIsFalse();

    }

    @Test
    public void testArchiveExpiredContracts() {
        // Arrange
        LocalDate currentDate = LocalDate.now();
        List<Contrat> mockExpiredContrats = new ArrayList<>();
        when(contratRepository.findByArchiveIsFalseAndDateFinContrat(currentDate)).thenReturn(mockExpiredContrats);

        // Act
        contratService.archiveExpiredContracts();

        // Assert
        verify(contratRepository, times(1)).findByArchiveIsFalseAndDateFinContrat(currentDate);
    }

}


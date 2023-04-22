package edu.msudenver.chat.group;

import edu.msudenver.group.Group;
import edu.msudenver.group.GroupRepository;
import edu.msudenver.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    //@Autowired
    //private MockMvc mockMvc;

    @Mock
    private GroupRepository groupRepository;

   // @MockBean
   // private EntityManagerFactory entityManagerFactory;

    @Mock
    private EntityManager entityManager;

    //@SpyBean
    private GroupService groupService;

    @BeforeEach
    public void setup() {
        groupService = new GroupService();
        groupService.entityManager = entityManager;
        groupService.groupRepository = groupRepository;
    }

    @Test
    public void testGetGroup() throws Exception {

        LocalDateTime dt = LocalDateTime.now();

        Group expectedGroup = new Group(1L,"New Group", dt);

        Mockito.when(groupRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedGroup));

        Group actualGroup = groupService.getGroup(1L);

        assertEquals(expectedGroup.getGroupID(),actualGroup.getGroupID());
        assertEquals(expectedGroup.getGroupName(),actualGroup.getGroupName());
        assertEquals(expectedGroup.getGroupCreationDate(),actualGroup.getGroupCreationDate());
    }

    @Test
    public void testGetGroupNotFoundException() throws Exception {
    }

    @Test
    public void testSaveGroup() throws Exception {
    }

    @Test
    public void testSaveGroupDuplicateException() throws Exception {
    }

    @Test
    public void testSaveGroupInvalidDataException() throws Exception {
    }

    @Test
    public void testDeleteGroup() throws Exception {
    }

    @Test
    public void testDeleteGroupNotFoundException() throws Exception {
    }

}

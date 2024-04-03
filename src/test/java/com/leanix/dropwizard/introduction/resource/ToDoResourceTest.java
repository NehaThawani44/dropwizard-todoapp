package com.leanix.dropwizard.introduction.resource;

import static org.assertj.core.api.Assertions.assertThat;


public class ToDoResourceTest {
/*

    private static final ToDoRepository mockRepository = mock(ToDoRepository.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ToDoResource(mockRepository,5))
            .build();

    @Test
    public void testAddTodo() {
        ToDo newTodo = new ToDo();
        newTodo.setTitle("New Todo");
        newTodo.setDescription("This is a new todo.");

        when(mockRepository.insert(any(ToDo.class))).thenReturn(newTodo);

        ToDo response = resources.target("/todos")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(newTodo, MediaType.APPLICATION_JSON_TYPE), ToDo.class);

        assertThat(response.getTitle()).isEqualTo(newTodo.getTitle());
        assertThat(response.getDescription()).isEqualTo(newTodo.getDescription());
        verify(mockRepository).insert(any(ToDo.class));
    }

*/

}

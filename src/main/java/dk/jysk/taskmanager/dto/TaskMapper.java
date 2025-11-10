package dk.jysk.taskmanager.dto;

import dk.jysk.taskmanager.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDTO toDto(TaskEntity task);

    List<TaskDTO> toDtoList(List<TaskEntity> tasks);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TaskEntity toEntity(TaskDTO taskDTO);
}

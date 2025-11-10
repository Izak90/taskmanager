package dk.jysk.taskmanager.dto;

import dk.jysk.taskmanager.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDTO toDto(TaskEntity task);

    List<TaskDTO> toDtoList(List<TaskEntity> tasks);

    TaskEntity toEntity(TaskDTO taskDTO);
}

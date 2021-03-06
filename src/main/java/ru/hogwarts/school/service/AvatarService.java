package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    long uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Avatar findAvatar(Long id);

    List<Avatar> getAvatarPage(int page, int size);

}

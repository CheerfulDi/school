package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService{

    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${students.avatars.dir.path}")
    private String avatarsDir;

    public static final int IMAGE_BLOCK_BUFFER_SIZE = 1024;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;


    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public long uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method to upload an avatar");
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            logger.error("Student with this id doesn't exist: " + studentId);
            throw new IllegalArgumentException("Student with this id doesn't exist: " + studentId);
        }

        Path filePath = createImageFilePath(avatarFile, student);
        saveImageToFile(avatarFile, filePath);

        Avatar avatar = findOrCreateAvatar(studentId);
        updateAvatar(avatarFile, student, filePath, avatar);
        return avatarRepository.save(avatar).getId();

    }

    @Override
    public Avatar findAvatar(Long id) {
        logger.info("Was invoked method to find an avatar by id");
        return avatarRepository.getById(id);
    }

    @Override
    public List<Avatar> getAvatarPage(int page, int size) {
        logger.info("Was invoked method to get the page of avatars by size");
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).toList();
    }

    private Path createImageFilePath(MultipartFile avatarFile, Student student) throws IOException {
        Path filePath = Path.of(avatarsDir, student + getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        return filePath;
    }

    private void saveImageToFile(MultipartFile avatarFile, Path filePath) throws IOException {
        try (
            BufferedInputStream bis = new BufferedInputStream(avatarFile.getInputStream(), IMAGE_BLOCK_BUFFER_SIZE);
            BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE_NEW), IMAGE_BLOCK_BUFFER_SIZE)
        ) {
            bis.transferTo(bos);
        }
    }

    private void updateAvatar(MultipartFile avatarFile, Student student, Path filePath, Avatar avatar) throws IOException {
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
    }

    private Avatar findOrCreateAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}


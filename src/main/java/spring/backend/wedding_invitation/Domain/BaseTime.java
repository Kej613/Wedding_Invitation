package spring.backend.wedding_invitation.Domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // JPA Entity 클래스들이 BaseTime class를 상속할 경우 BaseTime 필드 인식
@EntityListeners(AuditingEntityListener.class)  // 생성시간과 수정시간을 필드로 갖는 부모클래스 생성
public class BaseTime {
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

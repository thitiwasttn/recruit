package com.thitiwas.recruit.recruit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_video")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_video_id")
    private Long id;

    @Column(name = "video_name")
    private String videoName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id" , insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private User user;
}

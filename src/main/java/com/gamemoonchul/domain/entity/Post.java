package com.gamemoonchul.domain.entity;

import com.gamemoonchul.application.converter.JsonStringListConverter;
import com.gamemoonchul.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private List<VoteOptions> voteOptions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false)
    private List<Comment> comments;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private String title;

    private String content;

    @Convert(converter = JsonStringListConverter.class)
    private List<String> tags;

    @Builder.Default
    @Column(name = "view_count")
    private Long viewCount = 0L;
    @Builder.Default
    @Column(name = "comment_count")
    private Long commentCount = 0L;
    @Builder.Default
    @Column(name = "vote_count")
    private Long voteCount = 0L;

    @Builder.Default
    @Column(name = "vote_ratio")
    private Double voteRatio = 0.0;

    public void commentCountDown() {
        this.commentCount--;
    }

    public void addVoteOptions(List<VoteOptions> voteOptions) {
        if (this.voteOptions == null) {
            this.voteOptions = new ArrayList<VoteOptions>();
        }
        this.voteOptions.addAll(voteOptions);
    }

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<Comment>();
        }
        this.comments.add(comment);
        commentCount++;
    }

    public Double getMinVoteRatio() {
        int totalVoteCount = voteOptions.stream()
                .mapToInt(voteOption -> voteOption.getVotes()
                        .size())
                .sum();
        if (totalVoteCount == 0) {
            return 0.0;
        }
        double firstIndexVoteRatio = (double) voteOptions.get(0)
                .getVotes()
                .size() / (double) totalVoteCount * 100;
        return Math.min(100.0 - firstIndexVoteRatio, firstIndexVoteRatio);
    }
}

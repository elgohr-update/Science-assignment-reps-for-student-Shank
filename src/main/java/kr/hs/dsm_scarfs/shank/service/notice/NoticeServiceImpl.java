package kr.hs.dsm_scarfs.shank.service.notice;

import kr.hs.dsm_scarfs.shank.entites.notice.Notice;
import kr.hs.dsm_scarfs.shank.entites.notice.repository.NoticeRepository;

import kr.hs.dsm_scarfs.shank.exceptions.ApplicationNotFoundException;
import kr.hs.dsm_scarfs.shank.payload.response.ApplicationListResponse;
import kr.hs.dsm_scarfs.shank.payload.response.NoticeContentResponse;
import kr.hs.dsm_scarfs.shank.payload.response.NoticeResponse;

import kr.hs.dsm_scarfs.shank.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService, SearchService {

    private final NoticeRepository noticeRepository;

    @Override
    public ApplicationListResponse getNoticeList(Pageable page) {
        return this.searchApplication("", page);
    }

    @Override
    public NoticeContentResponse getNoticeContent(Integer noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(ApplicationNotFoundException::new);

        Notice preNotice = noticeRepository.findTop1ByIdBeforeOrderByIdAsc(noticeId)
                .orElseGet(() -> Notice.builder().build());

        Notice nextNotice = noticeRepository.findTop1ByIdAfterOrderByIdAsc(noticeId)
                .orElseGet(() -> Notice.builder().build());


        noticeRepository.save(notice.view());

        return NoticeContentResponse.builder()
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .createdAt(notice.getCreatedAt())
                    .view(notice.getView())
                    .nextNoticeTitle(nextNotice.getTitle())
                    .preNoticeTitle(preNotice.getTitle())
                    .nextNoticeId(nextNotice.getId())
                    .preNoticeId(preNotice.getId())
                    .build();
    }

    @Override
    public ApplicationListResponse searchApplication(String query, Pageable page) {
        Page<Notice> noticePage = noticeRepository.findAllByTitleContainsOrContentContains(query, query, page);

        List<NoticeResponse> noticeResponses = new ArrayList<>();

        for (Notice notice : noticePage) {
            String preViewContent = notice.getContent().substring(0, Math.min(50, notice.getContent().length()));
            noticeResponses.add(
                    NoticeResponse.builder()
                            .noticeId(notice.getId())
                            .view(notice.getView())
                            .title(notice.getTitle())
                            .createdAt(notice.getCreatedAt())
                            .preViewContent(preViewContent)
                            .build()
            );
        }


        return ApplicationListResponse.builder()
                .totalElements((int) noticePage.getTotalElements())
                .totalPages(noticePage.getTotalPages())
                .applicationResponses(noticeResponses)
                .build();
    }

}

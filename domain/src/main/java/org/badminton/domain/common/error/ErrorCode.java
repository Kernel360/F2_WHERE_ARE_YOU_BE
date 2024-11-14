package org.badminton.domain.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 Errors
    BAD_REQUEST(400, "클라이언트 요청의 형식이나 내용이 잘못되었습니다."),
    INVALID_PARAMETER(400, "요청한 파라미터의 형식이나 내용에 오류가 있습니다."),
    INVALID_RESOURCE(400, "요청한 리소스의 내용에 오류가 있습니다."),
    MISSING_PARAMETER(400, "필수 파라미터가 지정되지 않았습니다."),
    LIMIT_EXCEEDED(400, "파라미터 또는 리소스 속성값이 제한을 초과했습니다."),
    OUT_OF_RANGE(400, "파라미터 또는 리소스 속성값이 범위를 벗어났습니다."),
    FILE_NOT_EXIST(400, "파일이 존재하지 않거나 잘못된 파일입니다."),
    VALIDATION_ERROR(400, "입력값 검증에 실패했습니다"),

    // 401 Errors
    UNAUTHORIZED(401, "요구되는 인증 정보가 누락되었거나 잘못되었습니다."),

    // 403 Errors
    FORBIDDEN(403, "요청이 거부되었습니다."),
    ACCESS_DENIED(403, "리소스에 대한 접근이 제한되었습니다."),
    LIMIT_EXCEEDED_403(403, "리소스의 제한 설정을 초과했습니다."),
    OUT_OF_RANGE_403(403, "리소스의 제한 범위를 벗어났습니다."),

    // 404 Errors
    NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),
    JWT_COOKIE_NOT_FOUND(404, "JWT 쿠키를 찾을 수 없습니다."),

    // RESOURCE_NOT_EXIST
    RESOURCE_NOT_EXIST(404, "특정 리소스를 찾을 수 없습니다."),
    MEMBER_NOT_EXIST(404, "해당하는 회원이 존재하지 않습니다."),
    CLUB_NOT_EXIST(404, "해당하는 동호회가 존재하지 않습니다."),
    LEAGUE_NOT_EXIST(404, "해당하는 경기 일정이 존재하지 않습니다."),
    BRACKET_NOT_EXIST(404, "해당하는 경기게 아직 대진표가 만들어지지 않았습니다."),
    MATCH_NOT_EXIST(404, "해당하는 대진이 존재하지 않습니다."),
    SET_NOT_EXIST(404, "해당하는 세트는 존재하지 않습니다."),
    MEMBER_NOT_JOINED_CLUB(404, "해당하는 회원은 동호회에 가입하지 않았습니다."),
    CLUB_MEMBER_NOT_EXIST(404, "해당하는 회원은 해당 동호회에 아직 가입하지 않았습니다."),
    MATCH_DETAILS_NOT_EXIST(404, "해당하는 게임의 상세 정보가 아직 초기화되지 않았습니다."),
    IMAGE_FILE_NOT_FOUND(404, "파일의 형식이 잘못되었습니다."),

    // 409 Errors
    CONFLICT(409, "리소스 충돌이 발생했습니다."),
    ALREADY_EXIST(409, "리소스가 이미 존재합니다."),
    CLUB_MEMBER_ALREADY_EXIST(409, "이미 해당 동호회에 가입을 완료한 회원입니다."),
    LEAGUE_RECRUITING_ALREADY_COMPLETED(409, "이미 경기 일정에 모집 인원이 다 채워졌습니다."),
    CLUB_MEMBER_ALREADY_OWNER(409, "이미 해당 동호회를 생성하여 동호회장으로 가입을 완료한 회원입니다."),

    // RESOURCE_ALREADY_EXIST
    RESOURCE_ALREADY_EXIST(409, "특정 리소스가 이미 존재합니다."),
    CLUB_NAME_ALREADY_EXIST(409, "이미 존재하는 동호회 이름입니다."),
    LEAGUE_ALREADY_EXIST(409, "이미 존재하는 경기 일정입니다."),
    MATCH_ALREADY_EXIST(409, "이미 대진표가 만들어졌습니다."),
    MEMBER_ALREADY_JOINED_CLUB(409, "해당하는 회원은 이미 동호회에 가입을 완료했습니다."),
    MEMBER_ALREADY_APPLY_CLUB(409, "해당하는 회원은 이미 이 동호회에 가입 신청을 했습니다"),

    LEAGUE_ALREADY_PARTICIPATED(409, "이미 참여 신청을 완료한 경기 일정입니다."),
    LEAGUE_NOT_PARTICIPATED(409, "참여 신청을 하지 않는 경기입니다."),
    LEAGUE_PARTICIPATION_ALREADY_CANCELED(409, "이미 참여 신청을 취소한 경기 일정입니다."),
    CLUB_MEMBER_ALREADY_BANNED(409, "해당 회원은 이미 제제를 받은 상태입니다"),

    // 410 Errors
    DELETED(410, "요청한 리소스가 삭제되었습니다."),

    INVALID_PLAYER_COUNT(411, "아직 모집 인원이 채워지지 않았습니다."),
    LEAGUE_RECRUITING_MUST_BE_COMPLETED_WHEN_BRACKET_GENERATION(412, "대진표를 만들기 위해서는 모집이 종료되어야 합니다."),

    INSUFFICIENT_TIER(412, "현재 경기의 티어와 맞지 않습니다"),
    ONGOING_AND_UPCOMING_LEAGUE_CANNOT_BE_PAST(412, "메인 페이지에서 오늘 날짜 이전의 경기는 조회할 수 없습니다."),
    RECRUITMENT_END_DATE_AFTER_LEAGUE_START(412, "모집 마감 날짜는 경기 시작 날짜 이전이어야 합니다."),
    PLAYER_LIMIT_COUNT_DECREASED_NOT_ALLOWED(412, "경기 수정 시 기존 모집 제한 인원보다 더 적은 인원을 설정할 수 없습니다."),
    PLAYER_LIMIT_COUNT_MUST_BE_MULTIPLE_WHEN_DOUBLES_MATCH(412, "경기 최대 참여 인원은 복식 경기일 경우 4의 배수여야 합니다."),
    PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_FOUR(412, "경기 최대 참여 인원은 복식 경기일 경우 4 이상이어야 합니다."),
    LEAGUE_OWNER_CANNOT_CANCEL_LEAGUE_PARTICIPATION(412, "경기를 생성한 사람은 경기 참여를 취소할 수 없습니다."),
    LEAGUE_CANNOT_BE_CANCELED_WHEN_IS_NOT_RECRUITING(412, "경기 모집 중일 때맏 경기를 취소할 수 있습니다."),
    LEAGUE_PARTICIPANT_POWER_OF_TWO(412, "토너먼트 경기에서는 경기 참여 인원이 2의 제곱이어야 합니다"),

    // 500 Errors
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),

    // 503 Errors
    SERVICE_UNAVAILABLE(503, "일시적인 서버 오류입니다.");

    private final int httpStatusCode;
    private final String description;
}

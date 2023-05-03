# 노트 restfull api 설계

## 1. 도메인 설계

### member
    member_id            -- 회원고유번호
    login_id             -- 로그인 아이디
    password             -- 비밀번호(암호화)
    created              -- 회원가입 일자
    category             -- 목록

### note
    note_id             -- 노트고유번혼
    member              -- 작성자
    category            -- 목록
    content             -- 내용
    created             -- 회원가입 일자
    edited              -- 수정일자

### category
    category_id         -- 목록고유번호
    name                -- 이름

## 2. 기능
> 회원기능
>> <strike>회원가입, 로그인, 비즈닉스 로직 </strike>(노트조회, 카테고리 조회)
> 
> 노트 기능
>> <strike>노트생성, 노트수정, 노트 삭제</strike>
>
> 카테고리 기능
>> 카테고리 생성, 카테고리 수정, 카테고리 삭제


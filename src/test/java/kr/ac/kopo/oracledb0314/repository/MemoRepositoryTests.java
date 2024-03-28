package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

//    MemoRepository의  save(memo Entity 객체의 참조값)를 호출해서 insert한다.
    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Dummy Data Test" + i).build();
            memoRepository.save(memo);
        });
    }

//    MemoRepository의  findById(Memo Entity 객체의 Id로 설정된 필드값)를 호출해서 select한다.
//    findById() 호출되면 바로 select()문을 실행한다.
//    findById()는 NullPointerException이 발생하지 않도록 Null 체크를 한다.
    @Test
    public void testSelect(){
        // 데이터베이스에 존재하는 mno
        Long mno = 100l;

        Optional<Memo> result = memoRepository.findById(mno); //조회 작업

        System.out.println("==================================");

        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

//    MemoRepository의  getOne(Memo Entity 객체의 Id로 설정된 필드값)를 호출해서 select한다.
//    getOne은 호출되면 바로 실행하지 않고 Memo Entity가 필요할 때 select를 실행한다.
    @Transactional
    @Test
    public void testSelect2(){
        // 데이터베이스에 존재하는 mno
        Long mno = 100l;

        Memo memo = memoRepository.getOne(mno); //조회 작업

        System.out.println("==================================");

        System.out.println(memo);

    }

//    MemoRepository의  save(memo Entity 객체의 참조값)를 호출해서 update한다.
//    save()는 호출되면 먼저 select를 하기 때문에 기존에 Entity가 있을 때는 update를 실행한다.
    @Test
    public void testUpdate(){

        Memo memo = Memo.builder().mno(100l).memoText("Update Dummy Data 95").build();

        Memo memo1 = memoRepository.save(memo);

        System.out.println(memo);
    }

//    MemoRepository의  deleteById(memoEntity의 mno 값)을 호출해서 delete 한다.
    @Test
    public void testDelete(){

        long mno = 100l;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
//        1 페이지 당 10개의 Entity
        Pageable pageable = PageRequest.of(1,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }

        System.out.println("==================================");

        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Page Number: " + result.getNumber());
        System.out.println("Page Size: " + result.getSize());
        System.out.println("Has Next Page?: " + result.hasNext());
        System.out.println("Is First Page?: " + result.isFirst());
    }

    @Test
    public void testSort(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println("number:" + memo.getMno() + ", content: " + memo.getMemoText());
        });
    }
}

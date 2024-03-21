package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Dummy Data Test" + i).build();
            memoRepository.save(memo);
        });
    }

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

    @Transactional
    @Test
    public void testSelect2(){
        // 데이터베이스에 존재하는 mno
        Long mno = 100l;

        Memo memo = memoRepository.getOne(mno); //조회 작업

        System.out.println("==================================");

        System.out.println(memo);

    }

    @Test
    public void testUpdate(){

        Memo memo = Memo.builder().mno(100l).memoText("Update Dummy Data 95").build();

        Memo memo1 = memoRepository.save(memo);

        System.out.println(memo);
    }

    @Test
    public void testDelete(){

        long mno = 100l;

        memoRepository.deleteById(mno);
    }
}

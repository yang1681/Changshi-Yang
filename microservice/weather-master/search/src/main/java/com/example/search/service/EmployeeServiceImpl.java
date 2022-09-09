package com.example.search.service;

import com.example.search.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final ExecutorService pool;
    private final RestTemplate ribbonRestTemplate;

    @Autowired
    public EmployeeServiceImpl(ExecutorService pool, RestTemplate ribbonRestTemplate) {
        this.pool = pool;
        this.ribbonRestTemplate = ribbonRestTemplate;
    }

    @Override
    public Map<Integer, Map[]> fetchAllEmployeesByAges(List<Integer> ages) {
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for (int age : ages) {
            if (age < 0) try {
                throw new InvalidInputException(new String("age not valid"));
            } catch (InvalidInputException e) {
                System.out.println("age not valid");
            }
            completableFutureList.add(
                    CompletableFuture.supplyAsync(
                            () -> ribbonRestTemplate.getForObject("http://employee-service/employees?age=" + age, HashMap[].class)
                            , pool
                    )
            );
        }
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(VOID -> {
                    Map<Integer, Map[]> res = new HashMap<>();
                    for (int i = 0; i < completableFutureList.size(); i++) {
                        res.put(ages.get(i), (Map[]) completableFutureList.get(i).join());
                    }
                    return res;
                }).join();
    }
}

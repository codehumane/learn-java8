package stream;

import java.util.Optional;

import lombok.Builder;

/**
 * Created by chogh on 10/1/16.<br/>
 * java8 학습을 위한 간단 데이터 클래스
 */
@Builder
@lombok.Data
class Data {

    private String name;

    public Optional<String> getNameOptional() {
        return Optional.of(name);
    }

    public Data concat(Data data) {
        this.name += data.getName();
        return this;
    }
}
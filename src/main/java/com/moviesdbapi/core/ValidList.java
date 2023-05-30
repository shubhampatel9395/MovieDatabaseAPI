package com.moviesdbapi.core;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class ValidList<E> implements List<E> {
	@Valid
	@Delegate
    private List<E> list = new ArrayList<>();
}

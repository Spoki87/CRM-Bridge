package com.bridge.zoho.model.request;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CrmRequest<T> {
    private List<T> data;
}

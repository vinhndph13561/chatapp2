package com.vmo.chatapp2.utils;

import java.util.List;

public interface ICommonService<BO,Bean,Form> {
    public List<BO> findAll();

    public BO findById(Long id);

    public boolean saveOrUpdate(Form entity);
}

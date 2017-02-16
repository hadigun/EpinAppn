/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluechip.epin.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Adigun HM
 */
public interface ResultSetConverter<T> {
   public ArrayList<T> toObject(ResultSet rs) throws SQLException; 
}

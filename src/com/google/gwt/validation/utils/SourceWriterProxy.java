/*
 GWT-Validation Framework - Annotation based validation for the GWT Framework

 Copyright (C) 2008  Christopher Ruffalo

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.google.gwt.validation.utils;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

/** Proxy useful for debuging. 
 * 
 * <p>
 * Created on 2009-04-03
 *
 * @author kardigen
 * @version $Id$
 */
public class SourceWriterProxy implements SourceWriter {
    
    private final SourceWriter m_delgee;
    
    String m_ident = "";
    
    public SourceWriterProxy( final SourceWriter sw){
        m_delgee = sw;
        
    }

    /** {@inheritDoc} */
    public void beginJavaDocComment() {
       m_delgee.beginJavaDocComment();
    }

    /** {@inheritDoc} */
    public void commit(final TreeLogger logger) {
        m_delgee.commit(logger);
    }

    /** {@inheritDoc} */
    public void endJavaDocComment() {
        m_delgee.endJavaDocComment();
    }

    /** {@inheritDoc} */
    public void indent() {
        m_ident += "  ";
        System.out.println(m_ident);
        m_delgee.indent();
    }

    /** {@inheritDoc} */
    public void indentln(final String s) {
        m_ident += "  ";
        System.out.println(m_ident + s);
        m_delgee.indentln(s);
    }

    /** {@inheritDoc} */
    public void outdent() {
        if(m_ident.length() > 1){
            m_ident = m_ident.substring(0,m_ident.length()-2);
        }
        m_delgee.outdent();
    }

    /** {@inheritDoc} */
    public void print(final String s) {
        System.out.print(s);
        m_delgee.print(s);
    }

    /** {@inheritDoc} */
    public void println() {
        System.out.println();
        System.out.print(m_ident);
        m_delgee.println();
    }

    /** {@inheritDoc} */
    public void println(final String s) {
        System.out.println(s);
        System.out.print(m_ident);
        m_delgee.println(s);
    }

}

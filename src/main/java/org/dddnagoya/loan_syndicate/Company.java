package org.dddnagoya.loan_syndicate;

/**
 * 企業。
 * <p>
 * このドメインでは、あるファシリティの出資者となる。
 * </p>
 * @author t_hara
 */
public class Company {
    
    /** id */
    private final long id;
    
    /**
     * 指定されたIDを識別子として持つ企業を構築する。
     * @param id
     */
    public Company(long id) {
        super();
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !getClass().isInstance(obj)) return false;
        return this.id == Company.class.cast(obj).id;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Company[" + id + "]";
    }
}

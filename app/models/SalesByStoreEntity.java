package models;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_by_store", schema = "sakila", catalog = "")
public class SalesByStoreEntity {
    private String store;
    private String manager;
    private BigDecimal totalSales;

    @Basic
    @Column(name = "store", nullable = true, length = 101)
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Basic
    @Column(name = "manager", nullable = true, length = 91)
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Basic
    @Column(name = "total_sales", nullable = true, precision = 2)
    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesByStoreEntity that = (SalesByStoreEntity) o;

        if (store != null ? !store.equals(that.store) : that.store != null) return false;
        if (manager != null ? !manager.equals(that.manager) : that.manager != null) return false;
        if (totalSales != null ? !totalSales.equals(that.totalSales) : that.totalSales != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = store != null ? store.hashCode() : 0;
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + (totalSales != null ? totalSales.hashCode() : 0);
        return result;
    }
}

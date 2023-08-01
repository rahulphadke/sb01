package ta.sb01;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//POJO
@Entity
@Table(name="emp")
public class Employee {
    @Id
    @Column(name="empno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

@NotBlank(message = "name cannot be blank")
@Size(min=2, max=15, message = "name should be of min 2 letters and max 15 letters")
    private String name;
@Min(value=1000,message="Min sal is 1000")
@Max(value=5000,message="Max sal is 5000")
    private float salary;

    public Employee(){}

    public Employee(int id, String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

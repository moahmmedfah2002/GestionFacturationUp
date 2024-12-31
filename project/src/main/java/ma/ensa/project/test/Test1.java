package ma.ensa.project.test;

import ma.ensa.project.entity.Permission;
import ma.ensa.project.entity.PermissionEnum;
import ma.ensa.project.entity.Role;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.MailService;
import ma.ensa.project.service.UserService;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test1 {

    @Test
    public void mail(){
        //kawtar.zouiher.02@gmail.com
        //bilallaariny01@gmail.com
        MailService mailService = new MailService();
        boolean m=mailService.send("fahlaouimohammed@gmail.com","test","chouf lta7t","C:\\Users\\pc\\Documents\\java2\\GestionFacturation\\project\\src\\main\\resources\\f.pdf");
        Assert.assertTrue(m);


    }
    @Test
    public void add() throws SQLException, ClassNotFoundException, IOException {
        UserService userService = new UserService();
        User user = new User();
        user.setNomUtilisateur("bilal");
        user.setMotDePasse("123");
        user.setRole(Role.ADMIN.toString());
        Permission permission=new Permission();
        permission.setNom(PermissionEnum.CREATE_CLIENT.toString());
        Permission permission1=new Permission();
        permission1.setNom(PermissionEnum.CREATE_FACTURE.toString());

        List<Permission> permissions=new ArrayList<Permission>();
        permissions.add(permission);
        permissions.add(permission1);
        userService.addUser(user,permissions);
        Assert.assertEquals(userService.getSession().getId(),31);


    }

}

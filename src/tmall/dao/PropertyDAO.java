package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {

	// ��ȡһ����Ʒ����������
	public int getTotal(int cid) {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {

			String sql = "select count(*) from Property where cid = " + cid;
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				total = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return total;
	}

	// ������Ʒ�������
	public void add(Property property) {
		String sql = "insert into Property values(null,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, property.getCategory().getId());// �����id
			ps.setString(2, property.getName());// ��������
			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);// ���Ե�id
				property.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��������
	public void update(Property property) {
		String sql = "update Property set cid = ?,name = ?,where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, property.getCategory().getId());
			ps.setString(2, property.getName());
			ps.setInt(3, property.getId());

			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ������
	public void delete(int id) {
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
			String sql = "delete from Property where id =" + id;
			s.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �������� ����id��ȡ����
	public Property get(String name, int cid) {
		Property property = null;
		String sql = "select * from Property where name = ? and cid = ? ";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, property.getName());
			ps.setInt(2, property.getCategory().getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				property = new Property();
				property.setName(name);
				int id = rs.getInt("id");
				property.setId(id);
				Category category = new CategoryDAO().get(cid);
				property.setCategory(category);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;

	}

	// ����id��ȡ����
	public Property get(int id) {
		Property property = new Property();

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
			String sql = "select * from Property where id = " + id;

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	// ���ݷ���id�г�����
	public List<Property> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);

	}

	// ���ݷ���id ��ʼλ�� ������ȡ�����б�
	public List<Property> list(int cid, int start, int count) {
		List<Property> properties = new ArrayList<Property>();

		String sql = "select * from Property where cid = ? order by id desc = limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Property property = new Property();
				int id = rs.getInt(1);
				property.setId(id);
				String name = rs.getString("name");
				property.setName(name);
				Category category = new CategoryDAO().get(cid);
				property.setCategory(category);
				
				properties.add(property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;

	}

}

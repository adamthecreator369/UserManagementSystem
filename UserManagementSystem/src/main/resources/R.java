package main.resources;

import java.awt.Color;


public final class R {
	public static final class string {
		public static final String app_name = "User Management System";
		public static final String create_btn = "CREATE";
		public static final String edit_btn = "EDIT";
		public static final String delete_btn = "DELETE";

		public static final String create_user = "Create User";
		public static final String delete_user = "Delete User";
		public static final String edit_user = "Edit User";
		public static final String find_user = "Find User";
		public static final String search = "Search";
		public static final String full_name_label = "Full Name: ";
		public static final String name_label = "Name / Username: ";
		public static final String username_label = "Username: ";
		public static final String password_label = "Password: ";
		public static final String user_type_label = "User Type: ";
		public static final String view_raw_data = "Raw Data";
		public static final String view_all_users = "All Users";
		public static final String view_admin_users = "Admin Users";
		public static final String view_reg_users = "Regular Users";
		public static final String save_changes_btn = "Save Changes";
		public static final String reset_btn = "Reset";
		public static final String back_btn = "Back";
		public static final String logout_btn = "LOGOUT";
		public static final String login_btn = "Login";
		public static final String search_btn = "SEARCH";
		public static final String[] combo_options = { "Administrator", "Regular" };
		public static final String activity_log = "Activity Log";
		public static final String not_logged_in = "Currently not logged in";
		public static final String search_user = "User Search";
	}

	public static final class dimens {
		public static final int app_width = 937;
		public static final int app_height = 737;
		public static final int lrg_btn_x = 35;
		public static final int lrg_btn_base_y = 98;
		public static final int lBtnDiffY = 150;
		public static final int lrg_btn_width = 240;
		public static final int lrg_btn_height = 120;
		public static final int title_label_width = 200;
		public static final int title_label_height = 72;
		public static final int title_label_x = 305;
		public static final int title_label_y = 80;
		public static final int label_width = 110;
		public static final int label_height = 46;
		public static final int label_x = 305;
		public static final int label_y_diff = 46;
		public static final int label_y = 140;
		public static final int field_x = label_x + label_width;
		public static final int subLayout1_field_x = field_x + 35;
		public static final int field_width = 250;
		public static final int field_height = 40;
		public static final int combo_width = 0; 
		public static final int combo_height = 45;
		public static final int txt_area_x = 305;
		public static final int txt_area_y = 383;
		public static final int txt_area_width = 590;
		public static final int txt_area_height = 280;
		public static final int scroll_pane_x = 305;
		public static final int scroll_pane_y = 383;
		public static final int scroll_pane_width = 590;
		public static final int scroll_pane_height = 280;
		public static final int current_user_x = 46;
		public static final int current_user_y = 673;
		public static final int current_user_width = 410;
		public static final int current_user_height = 46;
		public static final int view_btn_x_base = 303;
		public static final int small_btn_width = 147;
		public static final int small_btn_height = 40;
		public static final int view_btn_y = 338;
		public static final int view_btn_diff = 149;
		public static final int btm_rt_btn_x = 750;
		public static final int btm_rt_btn_y = 673;
		public static final int top_btn_y = 93;
		public static final int top_btn_base_x = view_btn_x_base + view_btn_diff * 2;
		public static final int subLayout1_btn_x = field_x + field_width - small_btn_width + 35;
		public static final int login_btn_x = field_x + field_width - small_btn_width;
		public static final int subLayout_btn1_y = label_y + field_height + 5;
		public static final int login_btn_y = label_y + field_height * 2 + 10;

	}

	public static final class color {
		public static final Color app_color = Color.LIGHT_GRAY;
		public static final Color current_user_label = Color.GRAY;
	}
}

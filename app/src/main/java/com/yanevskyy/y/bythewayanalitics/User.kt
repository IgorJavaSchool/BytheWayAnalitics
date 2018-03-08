package com.firebase.mm.myapplication

import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.util.*

/**
 * Created by MM on 29.01.2018.
 */


enum class Method(var link: String) {
    TRAIN("train"),
    BUS("bus"),
    CAR("car"),
    PLANE("plane"),
    HITCHHIKING("hitchhiking")
    //BOAT("boat")
}

enum class SocialNetwork(var link: String) {
    VK("VK"),
    WHATSAPP("WHATSAPP"),
    CS("CS"),
    FB("FB"),
    TG("TG")
}

data class User(var name: String = "",
                var lastName: String = "",
                var age: Int = 0,
                var id: String = "",
                var email: String = "",
                var phone: String = "",
                var countTrip: Int = 0,
                var flightHours: Long = 0,
                var cityFromLatLng: GeoPoint = GeoPoint(0.0, 0.0),
                var cityToLatLng: GeoPoint = GeoPoint(0.0, 0.0),
                var countries: Long = 0,
                var kilometers: Long = 0,
                var route: String = "",
                var cities: HashMap<String, String> = hashMapOf<String, String>(),
                var method: HashMap<String, Boolean> = hashMapOf(Method.BUS.link to false,
                        Method.TRAIN.link to false,
                        Method.PLANE.link to false,
                        Method.CAR.link to false,
                        Method.HITCHHIKING.link to false),
                var dates: HashMap<String, Long> = hashMapOf<String, Long>(),
                var budget: Long = 0,
                var budgetPosition: Int = 0,
                var city: String = "",
                var percentsSimilarTravel: Int = 0,
                var addInformation: String = "",
                var sex: Int = 0,
                var socialNetwork: HashMap<String, String> = hashMapOf<String, String>(),
                var data: Long = 0,
                var urlPhoto: String = "https://www.ischool.berkeley.edu/sites/default/files/default_images/avatar.jpeg",
                var catchingDate: Long = 0L) : Serializable

data class TeamUser(val email: String, val is_robot: String, val is_manager: String, val IsAgent: String, val is_test: String)
data class TeamUserData(val IsSuccess: Boolean, val InUser: TeamUser)

val s = "{\"IsSuccess\":true,\"InUser\":\n" +
        "    {\n" +
        "        \"id\":\"8906\", \"site\":\"\", \"link_ok\":\"\", \"link_vk\":\"\", \"link_fb\":\"\",\n" +
        "        \"link_in\":\"\", \"link_tw\":\"\", \"is_site_moderate\":\"0\", \"group_id\":\"1\", \"login\":\"moyboss71@gmail.com\",\n" +
        "        \"nickname\":\"user8906\", \"is_user_password\":\"1\", \"family\":\"\\u041c\\u0430\\u0441\\u043b\\u043e\\u0432\",\n" +
        "        \"firstname\":\"\\u041d\\u0438\\u043a\\u043e\\u043b\\u0430\\u0439\", \"patronymic\":\"\",\n" +
        "        \"email\":\"moyboss71@gmail.com\", \"icq\":\"\", \"skype\":\"\", \"passport_serial\":\"\",\n" +
        "        \"passport_number\":\"\",\n" +
        "        \"regdate\":\"2017-07-08 20:12:07\", \"logdate\":\"2017-07-16 23:19:06\",\n" +
        "        \"birthdate\":\"1979-12-28\", \"is_locked\":\"0\", \"is_deleted\":\"0\", \"is_logged_once\":\"1\",\n" +
        "        \"rating\":\"0\", \"points\":\"0\", \"last_ip\":\"85.115.248.85\", \"status\":\"\",\n" +
        "        \"status_date\":\"0000-00-00 00:00:00\", \"invited_by\":\"0\", \"invdate\":\"\",\n" +
        "        \"openid\":\"\", \"is_email_accepted\":\"1\", \"email_accept_code\":\"2217\",\n" +
        "        \"is_phone_accepted\":\"0\", \"phone_country\":\"\", \"phone_code\":\"\", \"phone_number\":\"\",\n" +
        "        \"phone_accept_code\":\"\", \"state\":\"1\", \"is_operator\":\"0\", \"is_manager\":\"0\", \"IsAgent\":\"0\",\n" +
        "        \"operator_owner\":\"0\", \"is_translator\":\"\", \"level\":\"0\", \"bonus_level\":\"\", \"bonus_level_changed\":\"\",\n" +
        "        \"is_polled\":\"0\", \"know_us_from\":\"\", \"referer\":\"\", \"is_test\":\"0\", \"is_outside_operator\":\"0\",\n" +
        "        \"is_mess_wo_moderate\":\"0\", \"name_for_messages\":\"\", \"expo_WikiUser_id\":\"0\",\n" +
        "        \"sex\":\"1\", \"old_partner_id\":\"\", \"is_partner\":\"0\", \"referral_id\":\"0\",\n" +
        "        \"track\":\"a466fb1f19c9162835ad9ba23990fd29\", \"is_supervisor\":\"0\", \"net_id\":\"\",\n" +
        "        \"news_count\":\"0\", \"news_date\":\"\", \"is_external\":\"0\", \"id_city\":\"\",\n" +
        "        \"id_country\":\"643\",\n" +
        "        \"about\":\"\\u0414\\u0430\\u0432\\u043d\\u043e \\u0441\\u043e\\u0431\\u0438\\u0440\\u0430\\u044e\\u0441\\u044c \\u043d\\u0430 \\u0437\\u0438\\u043c\\u0443 \\u0434\\u0430\\u0442\\u044c \\u0442\\u0443\\u0440\\u043d\\u0435)) \\u0438\\u0449\\u0443 \\u043f\\u043e\\u043f\\u0443\\u0442\\u0447\\u0438\\u043a\\u0430 \\u0438\\u043b\\u0438 \\u043f\\u043e\\u043f\\u0443\\u0442\\u0446\\u0438\\u0442\\u0446\\u0443\",\n" +
        "        \"is_about_moderate\":\"1\",\n" +
        "        \"city\":\"\\u0421\\u0442\\u0430\\u0432\\u0440\\u043e\\u043f\\u043e\\u043b\\u044c\", \"setage\":\"38\", \"comnews_count\":\"0\", \"comchat_count\":\"167\",\n" +
        "        \"id_owner\":\"\", \"name_owner\":\"\", \"is_t2t_partner\":\"0\",\n" +
        "        \"is_t2t_partner_extention\":\"0\", \"t2t_lock\":\"0\", \"is_t2t_linker\":\"0\",\n" +
        "        \"spasibo\":\"1\", \"is_robot\":\"0\", \"contact\":\"2\", \"t2t_deleted\":\"0\",\n" +
        "        \"avatar_data\":\"5888668c4bed26dd32f2130ea80035fe.jpg\", \"avatar_type\":\"1\",\n" +
        "        \"t2t_prt\":\"0\", \"t2t_cpa_webmaster\":\"\", \"is_500_sended\":\"1\", \"delete_at\":\"\",\n" +
        "        \"create_platform\":\"0\", \"imageurl\":\"5888668c4bed26dd32f2130ea80035fe.jpg\", \"family_Literal\":\"\\u041c.\",\n" +
        "        \"online\":\"\", \"name_country\":\"\\u0420\\u043e\\u0441\\u0441\\u0438\\u044f\", \"is_slave_for_current_user\":\"0\", \"Auths\":[],\n" +
        "        \"my_tours\":[], \"my_trips\":[{ \"name_tour\":\"\", \"id_trip\":\"2363\", \"is_operator\":\"0\", \"is_leader\":\"1\",\n" +
        "        \"is_admin\":\"0\", \"is_member\":\"1\", \"is_subscribe\":\"1\", \"is_search\":\"1\", \"is_moderate\":\"1\", \"is_deny\":\"0\", \"is_subscribed\":\"1\" }], \"wishes_count\":1\n" +
        "    },\"IsCreated\":false"


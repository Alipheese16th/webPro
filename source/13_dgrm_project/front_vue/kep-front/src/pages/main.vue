<template>
  <div class="home_container">
    <div class="home_banner">
      <div class="banner_wrap">
        <p class="large">KIST Europe</p>
        <p class="large">Forschungsgesellschaft MbH</p>
        <p style="margin-top: 28px">
          KIST Europe Was Established In 1996 In Saarbrücken, Germany,<br />
          As An Overseas Branch Of KIST In Seoul, Korea.
        </p>
      </div>
      <div class="home_myWork_wrap">
        <div class="myWork_wrap">
          <v-btn class="btn_hide">hide</v-btn>
          <!-- <v-btn class="btn_show">show</v-btn> -->
          <div class="myInfo_wrap">
            <div class="photo_wrap">
              <img src="~/assets/images/myphoto.png" alt="" />
              <v-btn class="btn_set">Setting</v-btn>
            </div>
            <div class="myInfo">
              <p>KIST EUROPE</p>
              <div class="name_info">
                <span class="large">반갑습니다!</span>
                <span class="large mrR8">안우진님</span>
                <v-btn class="btn_logout mrR16">로그아웃</v-btn>
                <span>메일</span>
                <span class="mrR16 txt_green bold">112</span>
                <span>메시지</span>
                <span class="txt_green bold">20</span>
              </div>
              <div class="small">
                <span>LAST ACCESS</span>
                <span class="mrR8">0316 18:24</span>
                <span>IP</span>
                <span>192.168.0.1</span>
              </div>
            </div>
          </div>
          <div class="myMenu_wrap">
            <div class="menu_list">
              <div class="item">MyMenu01</div>
              <div class="item">MyMenu02</div>
              <div class="item">MyMenu03</div>
              <div class="item">MyMenu04</div>
              <div class="item">MyMenu05</div>
              <div class="item">MyMenu06</div>
            </div>
          </div>
        </div>
      </div>
      <div class="notice_wrap">
        <div class="content">
          <div class="left_wrap">
            <div class="item">
              <img src="~assets/images/ico_project.svg" alt="" />
              <div>
                <p>173</p>
                <p class="label">total project</p>
              </div>
            </div>
            <div class="item">
              <img src="~assets/images/ico_task.svg" alt="" />
              <div>
                <p>173</p>
                <p class="label">total tasks</p>
              </div>
            </div>
            <div class="item">
              <img src="~assets/images/ico_member.svg" alt="" />
              <div>
                <p>173</p>
                <p class="label">members</p>
              </div>
            </div>
          </div>
          <div class="right_wrap">
            <div class="notice">
              <span class="txt-alarm">알림</span>
              <span class="txt-subject">2023년 4월 사이버 교육신청안내</span>
            </div>
            <div class="btn_wrap">
              <v-btn>prev</v-btn>
              <v-btn>next</v-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="home_icon_group">
      <div class="item">
        <img src="~/assets/images/home_icon--01.svg" alt="" />
        <span>전체</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--02.svg" alt="" />
        <span>공람</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--03.svg" alt="" />
        <span>전체게시물</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--04.svg" alt="" />
        <span>신규연구사업</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--05.svg" alt="" />
        <span>게시판</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--06.svg" alt="" />
        <span>결재할문서</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--07.svg" alt="" />
        <span>진행문서함</span>
      </div>
      <div class="item">
        <img src="~/assets/images/home_icon--08.svg" alt="" />
        <span>설정</span>
      </div>
    </div>
    <div class="home_grid_container">
      <div class="item">
        <div class="item_tit">
          <div class="txt-group">
            <span class="txt-category">Notification</span>
            <v-btn class="btn_more--member"></v-btn>
          </div>
          <span class="txt-tit">April 2023 Cyber Education Application Guide</span>
          <span class="sub-tit">Value Innovation Team 김민혁</span>
        </div>
        <div class="item_bottom">
          <span>2023.05.25</span>
          <div class="member_group">
            <img class="leader" src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
          </div>
        </div>
      </div>
      <div class="item">
        <div class="item_tit">
          <div class="txt-group">
            <span class="txt-category">Notification</span>
            <v-btn class="btn_more--member"></v-btn>
          </div>
          <span class="txt-tit">April 2023 Cyber Education Application Guide</span>
          <span class="sub-tit">Value Innovation Team 김민혁</span>
        </div>
        <div class="item_bottom">
          <span>2023.05.25</span>
          <div class="member_group">
            <img class="leader" src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
          </div>
        </div>
      </div>
      <div class="item">
        <div class="item_tit">
          <div class="txt-group">
            <span class="txt-category">Notification</span>
            <v-btn class="btn_more--member"></v-btn>
          </div>
          <span class="txt-tit">April 2023 Cyber Education Application Guide</span>
          <span class="sub-tit">Value Innovation Team 김민혁</span>
        </div>
        <div class="item_bottom">
          <span>2023.05.25</span>
          <div class="member_group">
            <img class="leader" src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
          </div>
        </div>
      </div>
      <div class="item">
        <div class="item_tit">
          <div class="txt-group">
            <span class="txt-category">Notification</span>
            <v-btn class="btn_more--member"></v-btn>
          </div>
          <span class="txt-tit">April 2023 Cyber Education Application Guide</span>
          <span class="sub-tit">Value Innovation Team 김민혁</span>
        </div>
        <div class="item_bottom">
          <span>2023.05.25</span>
          <div class="member_group">
            <img class="leader" src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
          </div>
        </div>
      </div>
      <div class="item">
        <div class="item_tit">
          <div class="txt-group">
            <span class="txt-category">Notification</span>
            <v-btn class="btn_more--member"></v-btn>
          </div>
          <span class="txt-tit">April 2023 Cyber Education Application Guide</span>
          <span class="sub-tit">Value Innovation Team 김민혁</span>
        </div>
        <div class="item_bottom">
          <span>2023.05.25</span>
          <div class="member_group">
            <img class="leader" src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
          </div>
        </div>
      </div>
      <div class="item">
        <div class="item_tit">
          <div class="txt-group">
            <span class="txt-category">Notification</span>
            <v-btn class="btn_more--member"></v-btn>
          </div>
          <span class="txt-tit">April 2023 Cyber Education Application Guide</span>
          <span class="sub-tit">Value Innovation Team 김민혁</span>
        </div>
        <div class="item_bottom">
          <span>2023.05.25</span>
          <div class="member_group">
            <img class="leader" src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
            <img src="~/assets/images/myphoto.png" alt="" />
          </div>
        </div>
      </div>
    </div>
    <div class="community_wrap">
      <div class="community">
        <div class="item">
          <div class="item_tit">
            <img src="~/assets/images/myphoto2.png" alt="" />
            <div class="txt-info mrL16">
              <span class="point-color fs-15">New Staff</span>
              <span class="gray-color2 fs-20 bold mrB10">Kim min kyu</span>
              <span class="gray-color2 fs-17">Bio Medical Center</span>
              <span class="gray-color">System Rearch Center Director</span>
            </div>
          </div>
          <div class="item_middle">A Technology Has Been Developed To Make Electrolytes For Solid State Batteries, Which Are Considered The Next-Generation Batteries</div>
          <div class="item_bottom">
            <span>Welcome, Kim min kyu</span>
            <div class="member_group"></div>
          </div>
        </div>
        <div class="item">
          <div class="item_tit ty--2">
            <span class="gray-color2 fs-20 bold mrB10">Comunication</span>
            <div class="btn_wrap">
              <v-btn alt="prev"></v-btn>
              <v-btn alt="next"></v-btn>
            </div>
          </div>
          <div class="item_middle ty--2">
            <ul class="list">
              <li>
                <div class="detail">
                  <img src="~/assets/images/myphoto.png" alt="" />
                  <span>Congratulations On Opening The Keyeast Europe <em>kim</em></span>
                  <span>Hydrogen Fuel</span>
                  <span>2023.11.12</span>
                </div>
                <v-btn class="btn_more--list">more</v-btn>
              </li>
              <li>
                <div class="detail">
                  <img src="~/assets/images/myphoto.png" alt="" />
                  <span>Congratulations On Opening The Keyeast Europe<em>park</em></span>
                  <span>Hydrogen Fuel</span>
                  <span>2023.11.12</span>
                </div>
                <v-btn class="btn_more--list">more</v-btn>
              </li>
              <li>
                <div class="detail">
                  <img src="~/assets/images/myphoto.png" alt="" />
                  <span>Congratulations On Opening The Keyeast Europe<em>jin</em></span>
                  <span>Hydrogen Fuel</span>
                  <span>2023.11.12</span>
                </div>
                <v-btn class="btn_more--list">more</v-btn>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  meta: {
    title: 'Home',
  },
  data() {
    return {}
  },
}
</script>

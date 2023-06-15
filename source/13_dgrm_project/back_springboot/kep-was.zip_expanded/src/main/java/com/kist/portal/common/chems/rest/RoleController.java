package com.kist.portal.common.chems.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.dto.RoleDto;
import com.kist.portal.common.chems.dto.RowStatusType;
import com.kist.portal.common.chems.service.MlangService;
import com.kist.portal.common.chems.service.RoleService;
import com.kist.portal.common.comm.dto.ApiDto;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 권한관리 관련 화면 API 모음 컨트롤러.
 * 
 * [고려사항]
 * TODO (확인필요 20230531/x) POST API: savedatas, 다국어 서비스 등의 DTO/RESULTMAP 코드 컨벤션 방향 확정필요.
 */
@RestController
@RequestMapping("/api/v1/common/chems")
@RequiredArgsConstructor
public class RoleController {

	// [서비스 모음]
	@NonNull
	private final RoleService service;
	
	@NonNull
	private final MlangService mlangservice;

	@NonNull
	private final SessionManager sessionManager;

	// [상수 모음]
	// 다국어 코드 기본값
	private final String MLANG_DEFAULT = "ZZZZZZZZZZZZZZZZZZZZ";

	// 권한 삭제 불가 메시지
	private final String FAIL_CANNOTDELETE = "Fail_CANNOTDELETE";

	/** saveRoleList: 권한 CRUD 조건별 로직 모음 */
	private final Map<RowStatusType, Consumer<RoleDto>> saveRoleListPolicy = new HashMap<RowStatusType, Consumer<RoleDto>>() {{
		put(RowStatusType.C, (param) -> saveRoleListByCreate(param));
		put(RowStatusType.U, (param) -> saveRoleListByUpdate(param));
		put(RowStatusType.D, (param) -> saveRoleListByDelete(param));
	}};

	/** saveMeunRoleList: 한 권한에 물리는 메뉴 CRUD 조건별 로직 모음 */
	private final Map<RowStatusType, Consumer<RoleDto>> saveMenuRoleListPolicy = new HashMap<RowStatusType, Consumer<RoleDto>>() {{
		put(RowStatusType.U, (param) -> saveMenuRoleListByUpdate(param));
	}};

	/** saveApiRoleList: 한 권한에 물리는 권한 CRUD 조건별 로직 모음  */
	private final Map<RowStatusType, Consumer<RoleDto>> saveApiRoleListPolicy = new HashMap<RowStatusType, Consumer<RoleDto>>() {{
		put(RowStatusType.U, (param) -> saveApiRoleListByUpdate(param));
	}};



	/**
	 * 권한 리스트 조회
	 * @param param
	 * @return
	 * @version 2023-05-31 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@GetMapping("/role")
	public ResponseEntity<List<RoleDto>> selectRoleList(
		@ModelAttribute RoleDto param
	) {
		return new ResponseEntity<>(service.selectRoleList(param), HttpStatus.OK);
	}
	
	/**
	 * 권한 다건 저장
	 * 정합성 1) 사용자에게 물린 권한이 하나라도 있을 경우, 삭제 처리 불가
	 * @param param
	 * @return
	 * @version 2023-06-01 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@PostMapping("/role")
	@Transactional
	public ResponseEntity<String> saveRoleList(
		@RequestBody RoleDto param
	) {
		final List<RoleDto> savedatas = param.getSavedatas();

		// 삭제 데이터가 하나라도 존재할 때, 정합성 통과하지 못한 데이터가 있으면 cannotDelete = true

		// 해당 권한에게 사용자가 물려있는지 확인하기
		final List<String> roleNosToDelete = 
			param.getSavedatas()
			.stream()
			.filter((item) -> item.getRowStatus().equals(RowStatusType.D))
			.map((item) -> item.getRoleNo())
			.collect(Collectors.toList());
		final int userCount = roleNosToDelete.size() > 0 ? service.countUserOfRole(roleNosToDelete) : 0;
		final boolean cannotDelete = userCount > 0; 
		if(cannotDelete){
			return new ResponseEntity<>(FAIL_CANNOTDELETE, HttpStatus.OK);
		} else {
			savedatas.forEach((saveDataItem) -> 
				saveRoleListPolicy.getOrDefault(
					saveDataItem.getRowStatus(),
					(target) -> {}
				).accept(saveDataItem)
			);
			return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
		}


	}

	/** saveRoleList - create 로직 */
	private void saveRoleListByCreate(RoleDto saveDataItem){
		// 권한 키 채번 후 insert
		saveDataItem.setRoleNo(service.selectRolekey());
		service.insertRoleList(saveDataItem);

		// 다국어 키 채번
		final Map<String, String> paramForMlangKey = new HashMap<>();
		paramForMlangKey.put("mlang_type_cd", "RL");

		// 다국어 테이블에 insert
		final Map<String, String> paramForMlangInsert = new HashMap<>();
		// === 새로 매핑
		paramForMlangInsert.put("mlang_no", mlangservice.selectMlangkey(paramForMlangKey));
		paramForMlangInsert.put("mlang_cntn", saveDataItem.getRoleExplain());
		paramForMlangInsert.put("menu_no", MLANG_DEFAULT);
		paramForMlangInsert.put("cd_grp_no", MLANG_DEFAULT);
		paramForMlangInsert.put("cd", MLANG_DEFAULT);
		// === 기존 roleDto 의 값
		final UserInfo user = sessionManager.get();
		paramForMlangInsert.put("SESS_USR_ID", saveDataItem.getSESS_USR_ID());
		paramForMlangInsert.put("lang_cd", user.getLangCd());
		paramForMlangInsert.put("mlang_type_cd", "RL");
		paramForMlangInsert.put("biz_cd", saveDataItem.getBusinessCode());
		paramForMlangInsert.put("mlang_abbr", saveDataItem.getRoleExplain());
		paramForMlangInsert.put("role_no", saveDataItem.getRoleNo());
		paramForMlangInsert.put("usg_yn", Character.toString(saveDataItem.getUsageYn()));

		mlangservice.insertMlangList(paramForMlangInsert);
	}

	/** saveRoleList - update 로직 */
	private void saveRoleListByUpdate(RoleDto saveDataItem){
		// update
		service.updateRoleList(saveDataItem);

		//다국어 테이블 update
		final Map<String, String> paramForMlangUpdate = new HashMap<>();
		paramForMlangUpdate.put("biz_cd", saveDataItem.getBusinessCode());
		paramForMlangUpdate.put("SESS_LANG", saveDataItem.getSESS_LANG());
		paramForMlangUpdate.put("mlang_cntn", saveDataItem.getRoleExplain());
		paramForMlangUpdate.put("mlang_abbr", saveDataItem.getRoleExplain());
		paramForMlangUpdate.put("SESS_USR_ID", saveDataItem.getSESS_USR_ID());
		paramForMlangUpdate.put("role_no", saveDataItem.getRoleNo());
		mlangservice.updateRoleMlangList(paramForMlangUpdate);
	}

	/**
	 * saveRoleList - delete 로직
	 * @param saveDataItem
	 */
	private void saveRoleListByDelete(RoleDto saveDataItem){
		// 권한 master table
		saveDataItem.setDeleteYn('Y');
		saveDataItem.setUsageYn('N');
		service.deleteRoleList(saveDataItem);

		//다국어 테이블에 delete
		final Map<String, String> paramForMlangKey = new HashMap<>();
		paramForMlangKey.put("mlang_type_cd", "RL");
		final Map<String, String> paramForMlangRemove = new HashMap<>();
		paramForMlangRemove.put("SESS_USR_ID", saveDataItem.getSESS_USR_ID());
		paramForMlangRemove.put("mlang_no", mlangservice.selectMlangkey(paramForMlangKey));
		mlangservice.deleteMlangList(paramForMlangRemove);
	}

	
	/**
	 * 사용자 ~ 권한 매핑 리스트 조회
	 * @param param
	 * @return
	 * @version 2023-05-31 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@GetMapping("/user-role")
	public ResponseEntity<List<RoleDto>> selectUserRoleList(@ModelAttribute RoleDto param) {
		return new ResponseEntity<>(service.selectUserRoleList(param), HttpStatus.OK);
	}

	/**
	 * 권한 ~ 메뉴 매핑 리스트 조회
	 * @param param
	 * @return
	 * @version 2023-05-31 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@GetMapping("/role-menu")
	public ResponseEntity<List<RoleDto>> selectRoleListMeunRole(@ModelAttribute RoleDto param) {
		return new ResponseEntity<>(service.selectRoleListMeunRole(param), HttpStatus.OK);
	}
	
	/**
	 * 한 권한에게 매핑된 메뉴 리스트 조회
	 * @param roleNo
	 * @return
	 * @version 2023-05-31 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@GetMapping("/role-menu/{roleNo}")
	public ResponseEntity<List<RoleDto>> selectMenuListMeunRole(
		@PathVariable String roleNo
	) {
		final RoleDto param = new RoleDto();
		param.setRoleNo(roleNo);

		return new ResponseEntity<>(service.selectMenuListMeunRole(param), HttpStatus.OK);
	}
	
	/**
	 * 권한 ~ 메뉴 매핑정보 저장
	 * @param param
	 * @return
	 * @version 2023-06-01 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@PostMapping("/role-menu")
	@Transactional
	public ResponseEntity<String> saveMeunRoleList(@RequestBody RoleDto param) {
		param.getSavedatas()
		.forEach((saveDataItem) -> {
				saveDataItem.setRoleNo(param.getRoleNo());

				saveMenuRoleListPolicy.getOrDefault(
					saveDataItem.getRowStatus(),
					(target) -> {}
				).accept(saveDataItem);
			});

		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}

	/** saveMeunRoleList 의 업데이트 로직 */
	private void saveMenuRoleListByUpdate(RoleDto param) {
		if(param.getRoleCheck().equals("1")){
			//권한 체크된 메뉴 저장
			service.insertMeunRoleList(param);
		} else {
			//권한 체크해제된 메뉴 저장
			service.updateMeunRoleList(param);
		}
	}
	
	/**
	 * 권한 ~ API 매핑정보 리스트 조회
	 * @param param
	 * @return
	 * @version 2023-05-31 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@GetMapping("/role-api")
	public ResponseEntity<List<ApiDto>> selectApiListApiRole(@ModelAttribute ApiDto param) {
		return new ResponseEntity<>(service.selectApiListApiRole(param), HttpStatus.OK);
	}
	
	/**
	 * 권한 ~ API 매핑정보 다건 저장
	 * @param param
	 * @return
	 * @version 2023-06-01 miri DTO/RESULTMAP 코드 컨벤션 맞춤
	 */
	@PostMapping("/role-api")
	@Transactional
	public ResponseEntity<String> saveApiRoleList(
		@RequestBody RoleDto param
	) {
		param.getSavedatas()
			.forEach((saveDataItem) -> {
				saveDataItem.setRoleNo(param.getRoleNo());

				saveApiRoleListPolicy.getOrDefault(
					saveDataItem.getRowStatus(),
					(target) -> {}
				).accept(saveDataItem);
			});
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}

	/** saveApiRoleList 의 update 로직 */
	private void saveApiRoleListByUpdate(RoleDto param){
		service.insertApiRoleList(param);
	} 
	

	/**
	 * [시스템관리/권한관리 액션]
	 * 권한에게 사용자 다건 매핑하기
	 * @param param role_no, usr_id[]
	 * @author miri
	 * @version 2023-05-30
	 */
	@PostMapping("/role-user")
	public ResponseEntity<String> modifyUser(@RequestBody RoleDto param){
		service.modifyUser(param);
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}

}

package com.aicheck.face.modules.levels.controller;

import java.util.List;

import javax.validation.Valid;
import com.aicheck.face.common.constant.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.aicheck.face.common.utils.BeanUtils;
import com.aicheck.face.common.utils.UpdateUtils;
import com.aicheck.face.modules.levels.entity.Levels;
import com.aicheck.face.modules.levels.entity.LevelsForm;
import com.aicheck.face.modules.levels.service.LevelsService;
import com.aicheck.face.vo.R;


@RestController
@RequestMapping("/v1/levels")
public class LevelsController {
	@Autowired
	private LevelsService levelsService;
	
	@GetMapping
	public R findAllList(@RequestParam(value="currentPage", defaultValue = "1")Integer currentPage, @RequestParam(value = "pageSize", defaultValue = "15")Integer pageSize) {
		Page<Levels> page = levelsService.findAllList(currentPage, pageSize);
		if(page==null){
			return R.error("/v1/levels/get=>page为空");
		}


		return R.ok(page.getContent());
	}
	
	@PostMapping
	public R save(@RequestBody @Valid List<Levels> levelsForm) {
		List<Levels> levels = BeanUtils.batchTransform(Levels.class, levelsForm);
		levels.forEach(level-> {
			level.setCreateId(1);
		});
		levels = levelsService.save(levels);
		return R.ok(levels);
	}
	
	@PutMapping("/{id}")
	public R update(@PathVariable Integer id, @RequestBody @Valid LevelsForm levelsForm) {
		Levels levels = levelsService.findById(id);
		if(null == levels) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		UpdateUtils.copyNullProperties(levelsForm, levels);
		levels = levelsService.update(levels);
		return R.ok();
	}
	
	@DeleteMapping("/{id}")
	public R delete(@PathVariable Integer id) {
		levelsService.delete(id);
		return R.ok();
	}
}
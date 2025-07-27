package com.geumjulee.meow_diary.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {
    
    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("title", "MeowDiary - 고양이와 함께하는 건강한 일상")
        return "index"
    }
    
    @GetMapping("/cats")
    fun cats(model: Model): String {
        model.addAttribute("title", "내 고양이들 - MeowDiary")
        return "cats"
    }
    
    @GetMapping("/health")
    fun health(model: Model): String {
        model.addAttribute("title", "건강 기록 - MeowDiary")
        return "health"
    }
    
    @GetMapping("/community")
    fun community(model: Model): String {
        model.addAttribute("title", "커뮤니티 - MeowDiary")
        return "community"
    }
    
    @GetMapping("/ai-advice")
    fun aiAdvice(model: Model): String {
        model.addAttribute("title", "AI 건강 조언 - MeowDiary")
        return "ai-advice"
    }
} 
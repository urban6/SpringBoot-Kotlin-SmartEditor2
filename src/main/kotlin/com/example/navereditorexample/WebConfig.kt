package com.example.navereditorexample

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver


@Configuration
class WebConfig : WebMvcConfigurer {


    @Value("\${image.upload.path}")
    lateinit var uploadImagesPath: String

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)

        registry.addResourceHandler("/static/img/editor/**")
            .addResourceLocations("file:///$uploadImagesPath/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(PathResourceResolver())
    }
}
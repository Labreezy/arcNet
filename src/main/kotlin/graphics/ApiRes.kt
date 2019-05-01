package graphics


/*
 Q: How can I display an image? What is ImTextureID, how does it works?
 A: ImTextureID is a void* used to pass renderer-agnostic texture references around until it hits your render function.
    Dear ImGui knows nothing about what those bits represent, it just passes them around. It is up to you to decide what you want the void* to carry!
    It could be an identifier to your OpenGL texture (cast GLuint to void*), a pointer to your custom engine material (cast MyMaterial* to void*), etc.
    At the end of the chain, your renderer takes this void* to cast it back into whatever it needs to select a current texture to render.
    Refer to examples applications, where each renderer (in a imgui_impl_xxxx.cpp file) is treating ImTextureID as a different thing.

       // The example OpenGL back-end uses integers to identify textures.
       // You can safely store an integer into a void* by casting it. e.g. (void*)(intptr_t)MY_GL_UINT to cast to void*.
       GLuint my_opengl_texture;
       glGenTextures(1, &my_opengl_texture);
       // [...] load image, render to texture, etc.
       ImGui::Image((void*)(intptr_t)my_opengl_texture, ImVec2(512,512));

       // The example DirectX11 back-end uses ID3D11ShaderResourceView* to identify textures.
       ID3D11ShaderResourceView* my_texture_view;
       device->CreateShaderResourceView(my_texture, &my_shader_resource_view_desc, &my_texture_view);
       ImGui::Image((void*)my_texture_view, ImVec2(512,512));

    To display a custom image/texture within an ImGui window, you may use ImGui::Image(), ImGui::ImageButton(), ImDrawList::AddImage() functions.
    Dear ImGui will generate the geometry and draw calls using the ImTextureID that you passed and which your renderer can use.
    You may call ImGui::ShowMetricsWindow() to explore active draw lists and visualize/understand how the draw data is generated.
    It is your responsibility to get textures uploaded to your GPU.
 */
interface ResApi {

    /**
     * @return this should return whatever is needed for ImGui
     * to display any *.png stored in /main/resources
     */
    fun getImage(): Unit

}


class Image(
    val v1: Long,
    val v2: String
)

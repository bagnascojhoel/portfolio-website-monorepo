<script lang="ts">
    import type {ProjectApplicationService} from '@application/ProjectApplicationService';

    import Icon from '@components/Icon.svelte';
    import ProjectCard from '@components/ProjectCard/ProjectCard.svelte';
    import ProjectCardSkeleton from '@components/ProjectCard/ProjectCardSkeleton.svelte';
    
    import {type Project, ComplexityCode} from '@domain/Project';
    import type { Colors } from '@domain/Theme';
    import type Theme from '@domain/Theme';
    
    import { onMount, getContext, createEventDispatcher } from 'svelte';

    export let isOpen: boolean = true;
    export let disableClose: boolean = false;

    $: isLoading = projects.length === 0;

    const projectApplicationService: ProjectApplicationService = getContext('ProjectApplicationService');
    const theme: Theme = getContext('Theme');
    const colors: Colors = theme.colors;
    const dispatch = createEventDispatcher();
    
    
    let projects: Project[] = [];
    onMount(async () => {
        projects = await projectApplicationService.getProjects();
        projects.sort(sortProjects)
    });

    function sortProjects(a: Project, b: Project): number {
        const complexityValueForA = ComplexityCode[a.complexity.code] ?? ComplexityCode['complexity-medium'];
        const complexityValueForB = ComplexityCode[b.complexity.code] ?? ComplexityCode['complexity-medium'];
        
        if (complexityValueForA > complexityValueForB) {
            return -1;
        } else if (complexityValueForA < complexityValueForB) {
            return 1;
        } else {
            if (a.lastChangedDateTime.getTime() >= b.lastChangedDateTime.getTime()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
</script>

<aside
    class="
    fixed
    inset-0
    lg:left-[70%]
    bg-primary
    z-10
    overflow-y-auto
    translate-x-full
    ease-in-out
    duration-300
  "
    style={isOpen ? 'transform: translateX(0);' : ''}
>
    {#if !disableClose}
        <nav class="p-2 pb-0 flex flex-row-reverse">
            <button on:click={() => dispatch('close')}>
                <Icon name="close" size={50} color={colors['on-primary']} />
            </button>
        </nav>
    {/if}
    <div class="p-2 divide-y-8 divide-primary">
        {#if isLoading}
            <ProjectCardSkeleton isOpen />
            <ProjectCardSkeleton />
            <ProjectCardSkeleton />
            <ProjectCardSkeleton />
            <ProjectCardSkeleton />
            <ProjectCardSkeleton />
        {:else}
            {#each projects as project, i}
                <ProjectCard {project} isOpen={project.startsOpen || i === 0} />
            {/each}
        {/if}
    </div>
</aside>

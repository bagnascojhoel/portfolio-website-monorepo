<script lang="ts">
    import {
        EMAIL,
        PERSONAL_GITHUB_URL,
        PERSONAL_LINKEDIN_URL,
        BLOG_URL
    } from './contact-info';
    import CaptionOnHover from '@components/CaptionOnHover.svelte';
    import Icon from '@components/Icon.svelte';
    import type Theme from '@domain/Theme';
    import { getContext } from 'svelte';

    const { colors }: Theme = getContext('Theme');

    const GITHUB_LABEL = 'github';
    const LINKEDIN_LABEL = 'linkedin';
    const BLOG_LABEL = 'blog'
    const EMAIL_CAPTION = 'Copy to clipboard';

    function copyToClipboard(text: string) {
        navigator.clipboard.writeText(text);
    }
</script>

<section class="mt-12 mb-24 md:mt-24 lg:mt-0">
    <ul class="mt-7 flex flex-col justify-center gap-4 md:grid md:grid-cols-2 md:gap-8 ">
        <li>
            <a
                href={BLOG_URL}
                target="_blank"
                rel="noopener noreferrer"
            >
                <Icon
                    name="out-link"
                    color={colors['primary-variant']}
                    label={BLOG_LABEL}
                />
            </a>
        </li>
        <li>
            <a
                href={PERSONAL_LINKEDIN_URL}
                target="_blank"
                rel="noopener noreferrer"
            >
                <Icon
                    name="linkedin"
                    color={colors['primary-variant']}
                    label={LINKEDIN_LABEL}
                />
            </a>
        </li>
        <li>
            <a
                href={PERSONAL_GITHUB_URL}
                target="_blank"
                rel="noopener noreferrer"
            >
                <Icon
                    name="github"
                    color={colors['primary-variant']}
                    label={GITHUB_LABEL}
                />
            </a>
        </li>
        <li>
            <button on:click={() => copyToClipboard(EMAIL)}
                class="hover:cursor-grab show-clipboard-copy">
                <CaptionOnHover caption={EMAIL_CAPTION}>
                    <Icon
                        name="gmail"
                        color={colors['primary-variant']}
                        label={EMAIL}
                    />
                </CaptionOnHover>
            </button>
        </li>
    </ul>
</section>
